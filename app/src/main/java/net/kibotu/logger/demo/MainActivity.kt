package net.kibotu.logger.demo

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import net.kibotu.logger.*
import net.kibotu.logger.Logger.d
import net.kibotu.logger.Logger.e
import net.kibotu.logger.Logger.i
import net.kibotu.logger.Logger.v
import net.kibotu.logger.Logger.w
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

        startServerAndLogInterval()

        val openBrowserMessage = openBrowserMessage(port)
        v(openBrowserMessage)

        snack(openBrowserMessage, "Open in Browser") {
            "http://$formattedIpAddress:$port".openExternally()
        }

        // content.movementMethod = LinkMovementMethod.getInstance()
        // content.text = openBrowserMessage
    }

    private fun testInvokerMethod() {
        invokerMethod()
    }

    private fun testLogLevels() {
        v("verbose message")
        d("debug message")
        i("info message")
        w("warning message")
        e("error message")
    }

    private fun startServerAndLogInterval() {
        if (!subscribtion.isDisposed)
            subscribtion.dispose()

        subscribtion = CompositeDisposable()
        subscribtion.add(Observable.fromCallable { "keks #${count.incrementAndGet()}" }.repeatWhen { o -> o.concatMap { Observable.timer(1000, TimeUnit.MILLISECONDS) } }
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                v("${ResponseMessage(it)}")

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
        v(Logger.invoker())
    }


    fun String.openExternally() {

        var result = this

        if (!result.startsWith("http://") && !result.startsWith("https://")) {
            result = "http://$this"
        }

        application
            ?.startActivity(
                Intent(Intent.ACTION_VIEW)
                .apply {
                    addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                    addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
                    }
                    data = Uri.parse(result)
                })
    }
}