package net.kibotu.base

import android.app.Activity
import android.app.Application
import android.content.Context
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import java.io.File


/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */
@RunWith(RobolectricTestRunner::class)
@Config(application = AppStub::class, sdk = [26], manifest = Config.NONE)
abstract class BaseTest {

    val TAG: String = javaClass.simpleName

    fun context(): Context {
        return RuntimeEnvironment.application
    }

    fun cacheDir(): File {
        return context().cacheDir
    }

    @Before
    @Throws(Exception::class)
    open fun setUp() {

        val activity = Robolectric.buildActivity(ActivityStub::class.java).create().start().get()

    }
}

internal class AppStub : Application()
internal class ActivityStub : Activity()