package net.kibotu.logger

import net.kibotu.server.ResponseMessage
import net.kibotu.server.LoggingWebServer

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

class WebLogger : ILogger {

    override fun debug(tag: String, message: String) {
        LoggingWebServer.queue.add(ResponseMessage(tag + ".D " + message))
    }

    override fun verbose(tag: String, message: String) {
        LoggingWebServer.queue.add(ResponseMessage(tag + ".V " + message))
    }

    override fun information(tag: String, message: String) {
        LoggingWebServer.queue.add(ResponseMessage(tag + ".I " + message))
    }

    override fun warning(tag: String, message: String) {
        LoggingWebServer.queue.add(ResponseMessage(tag + ".W " + message))
    }

    override fun error(tag: String, message: String) {
        LoggingWebServer.queue.add(ResponseMessage(tag + ".E " + message))
    }

    override fun exception(throwable: Throwable) {
        LoggingWebServer.queue.add(ResponseMessage(throwable.message))
    }

    override fun toast(message: String) {
        LoggingWebServer.queue.add(ResponseMessage("Toast " + message))
    }

    override fun snackbar(message: String) {
        LoggingWebServer.queue.add(ResponseMessage("Snack " + message))
    }
}