package net.kibotu.logger.demo

import androidx.multidex.MultiDexApplication

import net.kibotu.logger.LogcatLogger
import net.kibotu.logger.Logger


/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        Logger.with(this)

        Logger.addLogger(LogcatLogger(), if (BuildConfig.DEBUG)
            Logger.Level.VERBOSE
        else
            Logger.Level.SILENT)

        invokerMethod()
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