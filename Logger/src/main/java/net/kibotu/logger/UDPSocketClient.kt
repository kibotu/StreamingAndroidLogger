package net.kibotu.logger

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import java.io.IOException
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress
import java.net.UnknownHostException
import java.util.concurrent.LinkedBlockingQueue

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

class UDPSocketClient(val ip: String, val port: Int) {

    private var activityLifecycleCallbacks: Application.ActivityLifecycleCallbacks? = null

    companion object {
        var isLogging = false
    }

    private fun log(message: String) {
        if (isLogging)
            Log.v("UDPSocketClient", message)
    }

    private var thread: Thread? = null

    /**
     * Determines if the socket main thread is currently running.
     */
    @Volatile
    private var isRunning = false

    private val socket = SynchronizedValue<DatagramSocket>()

    /**
     * Outbox for messages which will be send to the dispenser on next opportunity.
     */
    private val outputMessages = LinkedBlockingQueue<String>()

    // endregion

    /**
     * @see [http://alvinalexander.com/java/jwarehouse/android/tests/CoreTests/android/core/DatagramTest.java.shtml](http://alvinalexander.com/java/jwarehouse/android/tests/CoreTests/android/core/DatagramTest.java.shtml)
     */
    private fun createSocketHandler() = Runnable {

        if (isRunning)
            return@Runnable

        isRunning = true

        while (isRunning) {

            try {
                val packet = createDatagramPacket()
                socket.set(DatagramSocket())
                socket.get()?.soTimeout = SOCKET_TIMEOUT

                while (isRunning) {
                    try {
                        val message = outputMessages.take()
                        log("[SocketHandler] publish: $message")

                        socket.get()?.send(stringToPacket(message, packet))

                        safeSleep(16)
                    } catch (e: InterruptedException) {
                        log("${e.message}")
                    }

                }
            } catch (e: IOException) {
                log("${e.message}")
            } finally {
                try {
                    socket.get()?.close()
                } catch (ex: Exception) {
                    log("${ex.message}")
                }

            }

            safeSleep(RETRY_TIME_OUT)
        }

        isRunning = false
    }

    var SOCKET_TIMEOUT = 5000

    var RETRY_TIME_OUT = 1000L

    var PACKET_BUFFER_SIZE = 1024 * 64 - 1

    // region public api

    fun start() {
        log("[start] isRunning=$isRunning")
        if (isRunning)
            return

        thread = Thread(createSocketHandler(), "UDPSocketClient")
        thread!!.start()
    }

    fun stop() {
        log("[stop]  $this")
        isRunning = false
        thread?.interrupt()
        thread = null
    }

    fun send(message: String) {
        log("[send] $message")

        outputMessages.add(message)
    }

    @Throws(UnknownHostException::class)
    private fun createDatagramPacket(): DatagramPacket {
        val buffer = ByteArray(PACKET_BUFFER_SIZE)
        val packet = DatagramPacket(buffer, buffer.size)
        packet.address = InetAddress.getByName(ip)
        packet.port = port
        return packet
    }

    /**
     * Converts a given datagram packet's contents to a String.
     */
    private fun stringFromPacket(packet: DatagramPacket): String {
        return String(packet.data, 0, packet.length)
    }

    /**
     * Converts a given String into a datagram packet.
     */
    private fun stringToPacket(s: String, packet: DatagramPacket): DatagramPacket {
        val bytes = s.toByteArray()
        System.arraycopy(bytes, 0, packet.data, 0, bytes.size)
        packet.length = bytes.size
        return packet
    }

    // region lifecycle

    fun startWith(application: Application) {
        start()
        activityLifecycleCallbacks = createActivityLifecycleCallbacks()
        application.registerActivityLifecycleCallbacks(activityLifecycleCallbacks)
    }

    private fun createActivityLifecycleCallbacks(): Application.ActivityLifecycleCallbacks {
        return object : Application.ActivityLifecycleCallbacks {

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                start()
            }

            override fun onActivityStarted(activity: Activity) {

            }

            override fun onActivityResumed(activity: Activity) {

            }

            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStopped(activity: Activity) {

            }

            override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {

            }

            override fun onActivityDestroyed(activity: Activity) {
                stop()
            }
        }
    }

    fun onTerminate(application: Application) {
        application.unregisterActivityLifecycleCallbacks(activityLifecycleCallbacks)
    }

    // endregion

    private fun safeSleep(millisecond: Long) {
        try {
            Thread.sleep(millisecond)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}