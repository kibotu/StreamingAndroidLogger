package net.kibotu.logger.demo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import net.kibotu.logger.Logger

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Logger.snackbar("Hello World.")
    }
}