package net.kibotu.logger

import android.app.Application
import java.util.*



class UDPLogger(context: Application, ip: String, port: Int) : ILogger {

    private val udpSocketClient by lazy {
        UDPSocketClient(ip, port).apply {
            startWith(context)
        }
    }

    override fun exception(throwable: Throwable) {
        send("E", deviceId, "$throwable")
    }

    override fun snackbar(message: String) {
        send("S", deviceId, "$message")
    }

    private var deviceId: String? = UUID.randomUUID().toString().substring(0, 8)

    fun setDeviceId(deviceId: String): UDPLogger {
        this.deviceId = deviceId
        return this
    }

    override fun debug(tag: String, message: String) {
        send("D", deviceId, "$tag $message")
    }

    override fun verbose(tag: String, message: String) {
        send("V", deviceId, "$tag $message")
    }

    override fun information(tag: String, message: String) {
        send("I", deviceId, "$tag $message")
    }

    override fun warning(tag: String, message: String) {
        send("W", deviceId, "$tag $message")
    }

    override fun error(tag: String, message: String) {
        send("E", deviceId, "$tag $message")
    }

    override fun toast(message: String) {
        send("T", deviceId, message)
    }

    private fun send(logLevel: String, deviceId: String?, message: String) {
        udpSocketClient.send("$logLevel [Android:$deviceId] $message")
    }
}