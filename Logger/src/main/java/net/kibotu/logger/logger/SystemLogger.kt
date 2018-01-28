package net.kibotu.logger.logger

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

class SystemLogger : ILogger {

    override fun debug(tag: String, message: String) {
        System.out.println(tag + " " + message)
    }

    override fun verbose(tag: String, message: String) {
        System.out.println(tag + " " + message)
    }

    override fun information(tag: String, message: String) {
        System.out.println(tag + " " + message)
    }

    override fun warning(tag: String, message: String) {
        System.out.println(tag + " " + message)
    }

    override fun error(tag: String, message: String) {
        System.err.println(tag + " " + message)
    }

    override fun exception(throwable: Throwable) {
        throwable.printStackTrace(System.err)
    }

    override fun toast(message: String) {
        System.out.println(message)
    }

    override fun snackbar(message: String) {
        System.out.println(message)
    }
}