package net.kibotu.logger.demo

import androidx.multidex.MultiDexApplication
import net.kibotu.logger.Level

import net.kibotu.logger.LogcatLogger
import net.kibotu.logger.Logger
import net.kibotu.logger.Logger.logd
import net.kibotu.logger.Logger.loge
import net.kibotu.logger.Logger.logi
import net.kibotu.logger.Logger.logv
import net.kibotu.logger.Logger.logw
import net.kibotu.logger.WebLogger


/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        Logger.with(this)

        Logger.addLogger(LogcatLogger(), Level.VERBOSE)

        Logger.addLogger(WebLogger(), Level.SILENT)

        invokerMethod()

        logv("verbose message")
        logd("debug message")
        logi("info message")
        logw("warning message")
        loge("error message")
    }

    fun invokerMethod() {
        invokeMe()
    }

    private fun invokeMe() {
        Logger.v("hallo", Logger.invoker())
    }

    override fun onTerminate() {
        super.onTerminate()

        Logger.onTerminate()
    }
}