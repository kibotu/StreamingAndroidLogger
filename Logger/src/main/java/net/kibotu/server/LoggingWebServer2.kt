/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.kibotu.server

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.AssetManager
import android.net.Uri
import android.net.wifi.WifiManager
import android.text.TextUtils.isEmpty
import android.util.Log
import com.google.gson.GsonBuilder
import net.kibotu.ContextHelper.getApplication
import net.kibotu.logger.Logger
import java.io.*
import java.net.ServerSocket
import java.net.Socket
import java.net.URLDecoder
import java.nio.charset.Charset
import java.util.*
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingQueue


/**
 * Implementation of a very basic HTTP server. The contents are loaded from the assets folder. This
 * server handles one request at a time. It only supports GET method.
 */
internal class LoggingWebServer2(
        /**
         * The port number we listen to
         */
        private val port: Int,
        /**
         * [AssetManager] for loading files to serve.
         */
        private val assets: AssetManager
) : Runnable {

    /**
     * True if the server is running.
     */
    private var isRunning: Boolean = false

    /**
     * The [ServerSocket] that we listen to.
     */
    private var serverSocket: ServerSocket? = null

    /**
     * This method starts the web server listening to the specified port.
     */
    fun start() {
        Log.v(TAG, "[start]")
        isRunning = true
        Thread(this).start()
    }

    /**
     * This method stops the web server
     */
    fun stop() {
        Log.v(TAG, "[stop]")
        try {
            isRunning = false
            if (null != serverSocket) {
                serverSocket!!.close()
                serverSocket = null
            }
        } catch (e: IOException) {
            Log.e(TAG, "Error closing the server socket.", e)
        }

    }

    override fun run() {
        Log.v(TAG, "[run]")

        try {
            // Logger.v(TAG, "[run] Found free port at: " + port);

            serverSocket = ServerSocket(port)
            Log.v(TAG, "[run] Listening to port=" + serverSocket!!.localPort)

            while (isRunning) {
                val socket = serverSocket!!.accept()
                handle(socket)
                closeSilently(socket)
            }
        } catch (e: IOException) {
            Log.v(TAG, "" + e.message)
        }

    }

    /**
     * Respond to a request from a client.
     *
     * @param socket The client socket.
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun handle(socket: Socket) {
        var reader: BufferedReader? = null
        var output: PrintStream? = null
        try {
            // Read HTTP headers and parse out the route.
            var pathAndParams: String? = null
            reader = BufferedReader(InputStreamReader(socket.getInputStream()))
            var line = ""
            while (!isEmpty(line)) {
                line = reader.readLine()
                Logger.v(TAG, "[handle] " + line)
                if (line.startsWith("GET /")) {
                    val start = line.indexOf('/') + 1
                    val end = line.indexOf(' ', start)
                    pathAndParams = line.substring(start, end)
                    break
                }
            }

            // Output stream that we send the response to
            output = PrintStream(socket.getOutputStream())

            // Prepare the content to send.
            if (isEmpty(pathAndParams)) {
                pathAndParams = "index.html"
            }

            val uri = parseUri(pathAndParams!!)

            Log.v(TAG, "[request] " + uri)

            val bytes = createResponse(pathAndParams)
            if (null == bytes) {
                writeServerError(output)
                return
            }

            // Send out the content.
            output.println("HTTP/1.0 200 OK")
            output.println("Content-Type: " + detectMimeType(uri.path)!!)
            output.println("Content-Length: " + bytes.size)
            output.println()
            output.write(bytes)
            output.flush()
        } finally {
            closeSilently(output)
            closeSilently(reader)
        }
    }

    @Throws(IOException::class)
    private fun createResponse(pathAndParams: String): ByteArray? {
        Log.v(TAG, "[createResponse] " + pathAndParams)


        return when (pathAndParams) {
            "messages.json" -> {

                val list = ArrayList<ResponseMessage>()
                while (!queue.isEmpty())
                    list.add(queue.poll())

                val s = toJson(list)
                Log.v(TAG, "[response] " + s)
                s.toByteArray(Charset.forName("UTF-8"))

            }
            else -> pathAndParams.bytesFromAssets()
        }
    }

    /**
     * Writes a server error response (HTTP/1.0 500) to the given output stream.
     *
     * @param output The output stream.
     */
    private fun writeServerError(output: PrintStream) {
        output.println("HTTP/1.0 500 Internal Server Error")
        output.flush()
    }

    private fun String.stringFromAssets(): String {
        try {
            return assets.open(this).bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    private fun String.bytesFromAssets(): ByteArray = assets.open(this).use { ByteArray(it.available()).apply { it.read(this) } }

    /**
     * Detects the MIME type from the `fileName`.
     *
     * @param fileName The name of the file.
     * @return A MIME type.
     */
    private fun detectMimeType(fileName: String): String? {
        return if (isEmpty(fileName)) {
            null
        } else if (fileName.endsWith(".html")) {
            "text/html"
        } else if (fileName.endsWith(".js")) {
            "application/javascript"
        } else if (fileName.endsWith(".css")) {
            "text/css"
        } else if (fileName.endsWith(".json")) {
            "application/json"
        } else {
            "application/octet-stream"
        }
    }

    @Throws(UnsupportedEncodingException::class)
    private fun parseUri(url: String): Uri {
        return Uri.parse(URLDecoder.decode(url, "UTF-8"))
    }

    companion object {

        private val TAG = LoggingWebServer2::class.java.simpleName

        val queue: BlockingQueue<ResponseMessage> = LinkedBlockingQueue()

        /**
         * Quietly close a [AutoCloseable] dealing with nulls and exceptions.
         *
         * @param closeable to be closed.
         */
        @SuppressLint("NewApi")
        private fun closeSilently(closeable: AutoCloseable?) {
            try {
                if (null != closeable) {
                    closeable.close()
                }
            } catch (ignore: Exception) {
            }
        }

        @JvmStatic
        fun toJson(t: Any): String {
            return gson.toJson(t)
        }

        @JvmStatic
        fun <T> fromJson(json: String, clz: Class<T>): T {
            return gson.fromJson(json, clz)
        }

        private val gson = GsonBuilder().disableHtmlEscaping().create()

        @JvmStatic
        @SuppressLint("WifiManagerLeak")
        fun getAddressLog(port: Int): String {
            val wifiManager = getApplication()?.getSystemService(Context.WIFI_SERVICE) as? WifiManager
            val ipAddress = wifiManager?.connectionInfo?.ipAddress ?: 0
            @SuppressLint("DefaultLocale") val formattedIpAddress = String.format("%d.%d.%d.%d",
                    ipAddress and 0xff,
                    ipAddress shr 8 and 0xff,
                    ipAddress shr 16 and 0xff,
                    ipAddress shr 24 and 0xff)
            return "Open http://$formattedIpAddress:$port in your browser. If website can't be found: make sure device and pc are on the same network segment."
        }
    }
}