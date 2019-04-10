package net.kibotu.logger

import com.crashlytics.android.Crashlytics

class CrashlyticsLogger : ILogger {

    override fun debug(tag: String, message: String) = Crashlytics.log("$tag $message")

    override fun error(tag: String, message: String) = Crashlytics.log("$tag $message")

    override fun exception(throwable: Throwable) = Crashlytics.logException(throwable)

    override fun information(tag: String, message: String) = Crashlytics.log("$tag $message")

    override fun snackbar(message: String) = Crashlytics.log("$message")

    override fun toast(message: String) = Crashlytics.log("$message")

    override fun verbose(tag: String, message: String) = Crashlytics.log("$tag $message")

    override fun warning(tag: String, message: String) = Crashlytics.log("$tag $message")
}