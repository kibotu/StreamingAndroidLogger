package net.kibotu.logger

import java.lang.System.err
import java.lang.System.out

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

class SystemLogger : ILogger {

    override fun debug(tag: String, message: String) = out.println("$tag $message")

    override fun verbose(tag: String, message: String) = out.println("$tag $message")

    override fun information(tag: String, message: String) = out.println("$tag $message")

    override fun warning(tag: String, message: String) = out.println("$tag $message")

    override fun error(tag: String, message: String) = err.println("$tag $message")

    override fun exception(throwable: Throwable) = throwable.printStackTrace(err)

    override fun toast(message: String) = out.println(message)

    override fun snackbar(message: String) = out.println(message)
}