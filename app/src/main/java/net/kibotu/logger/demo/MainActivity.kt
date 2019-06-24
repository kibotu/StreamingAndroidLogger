package net.kibotu.logger.demo

import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.appcompat.app.AppCompatActivity
import com.exozet.android.core.extensions.openExternally
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import net.kibotu.logger.*
import net.kibotu.logger.Logger.logd
import net.kibotu.logger.Logger.loge
import net.kibotu.logger.Logger.logi
import net.kibotu.logger.Logger.logv
import net.kibotu.logger.Logger.logw
import net.kibotu.server.LoggingWebServer
import net.kibotu.server.ResponseMessage
import net.kibotu.server.formattedIpAddress
import net.kibotu.server.openBrowserMessage
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

class MainActivity : AppCompatActivity() {

    private var subscribtion = CompositeDisposable()

    private var count = AtomicInteger(0)

    private val port = 8080

    private var loggingWebServer: LoggingWebServer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Logger.addLogger(LogcatLogger(), Level.VERBOSE)
        Logger.addLogger(WebLogger(), Level.VERBOSE)
        Logger.addLogger(UDPLogger(application, "logs.papertrailapp.com", 1337), Level.VERBOSE)

        testInvokerMethod()
        testLogLevels()
        testLogBlocksLevels()

        startServerAndLogInterval()

        val openBrowserMessage = openBrowserMessage(port)
        logv(openBrowserMessage)

        snack(openBrowserMessage, "Open in Browser") {
            "http://$formattedIpAddress:$port".openExternally()
        }

        content.movementMethod = LinkMovementMethod.getInstance()
        content.text = openBrowserMessage
    }

    private fun testInvokerMethod() {
        invokerMethod()
    }

    private fun testLogLevels() {
        logv("verbose message")
        logd("debug message")
        logi("info message")
        logw("warning message")
        loge("error message")
    }

    private fun testLogBlocksLevels() {
        logv { "verbose message" }
        logd { "debug message" }
        logi { "info message" }
        logw { "warning message" }
        loge { "error message" }
    }

    private fun startServerAndLogInterval() {
        if (!subscribtion.isDisposed)
            subscribtion.dispose()

        subscribtion = CompositeDisposable()
        subscribtion.add(Observable.fromCallable { "keks #${count.incrementAndGet()}" }.repeatWhen { o -> o.concatMap { Observable.timer(1000, TimeUnit.MILLISECONDS) } }
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                logv("${ResponseMessage(it)}")

            }, { it.printStackTrace() }))

        loggingWebServer = LoggingWebServer(port, assets)
        loggingWebServer?.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        loggingWebServer?.stop()

        if (!subscribtion.isDisposed)
            subscribtion.dispose()
    }

    fun invokerMethod() {
        invokeMe()
    }

    private fun invokeMe() {
        logv(Logger.invoker())
    }
}