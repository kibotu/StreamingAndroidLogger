package net.kibotu.logger.demo

import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import net.kibotu.logger.Logger
import net.kibotu.server.LoggingWebServer
import net.kibotu.server.ResponseMessage
import net.kibotu.server.getIpAddressLog
import java.util.concurrent.TimeUnit

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

class MainActivity : AppCompatActivity() {

    private var TAG: String = javaClass.simpleName

    private var subscribe: Disposable? = null

    private var i = 0

    private var loggingWebServer: LoggingWebServer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        subscribe = Observable.fromCallable { "keks #" + ++i }.repeatWhen { o -> o.concatMap { Observable.timer(1000, TimeUnit.MILLISECONDS) } }
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ msg ->

                    LoggingWebServer.queue.add(ResponseMessage(msg))

                }, { it.printStackTrace() })


        loggingWebServer = LoggingWebServer(8080, assets)
        Logger.snackbar("Start.")
        Logger.v(TAG, getIpAddressLog(8080))
        loggingWebServer?.start()

        content.movementMethod = LinkMovementMethod.getInstance()
        content.text = getIpAddressLog(8080)
    }

    override fun onDestroy() {
        super.onDestroy()
        loggingWebServer?.stop()
        subscribe?.dispose()
    }
}