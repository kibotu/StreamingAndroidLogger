package net.kibotu.logger.demo

import android.support.multidex.MultiDexApplication

import net.kibotu.logger.LogcatLogger
import net.kibotu.logger.Logger


/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        Logger.with(this)
        Logger.addLogger(LogcatLogger(), Logger.Level.DEBUG)
    }

    override fun onTerminate() {
        super.onTerminate()

        Logger.onTerminate()
    }
}