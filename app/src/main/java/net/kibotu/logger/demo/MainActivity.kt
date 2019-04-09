package net.kibotu.logger.demo

import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import net.kibotu.logger.Level
import net.kibotu.logger.LogcatLogger
import net.kibotu.logger.Logger
import net.kibotu.logger.Logger.logd
import net.kibotu.logger.Logger.loge
import net.kibotu.logger.Logger.logi
import net.kibotu.logger.Logger.logv
import net.kibotu.logger.Logger.logw
import net.kibotu.logger.WebLogger
import net.kibotu.server.LoggingWebServer
import net.kibotu.server.ResponseMessage
import net.kibotu.server.openBrowserMessage
import java.util.concurrent.TimeUnit

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

class MainActivity : AppCompatActivity() {

    private var subscribe: Disposable? = null

    private var i = 0

    private var loggingWebServer: LoggingWebServer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Logger.addLogger(LogcatLogger(), Level.VERBOSE)
        Logger.addLogger(WebLogger(), Level.VERBOSE)

        invokerMethod()

        logv("verbose message")
        logd("debug message")
        logi("info message")
        logw("warning message")
        loge("error message")

        subscribe = Observable.fromCallable { "keks #" + ++i }.repeatWhen { o -> o.concatMap { Observable.timer(1000, TimeUnit.MILLISECONDS) } }
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                logv("${ResponseMessage(it)}")

            }, { it.printStackTrace() })

        loggingWebServer = LoggingWebServer(8080, assets)
        Logger.snackbar("Start.")
        val openBrowserMessage = openBrowserMessage(8080)
        logv(openBrowserMessage)
        loggingWebServer?.start()

        content.movementMethod = LinkMovementMethod.getInstance()
        content.text = openBrowserMessage
    }

    override fun onDestroy() {
        super.onDestroy()
        loggingWebServer?.stop()
        subscribe?.dispose()
    }

    fun invokerMethod() {
        invokeMe()
    }

    private fun invokeMe() {
        Logger.v("hallo", Logger.invoker())
    }
}