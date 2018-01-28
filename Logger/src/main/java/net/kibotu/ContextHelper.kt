package net.kibotu

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity

import java.lang.ref.WeakReference
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

object ContextHelper {

    private var application: WeakReference<Application>? = null

    private var context: WeakReference<Context>? = null

    @JvmStatic
    fun with(application: Application) {
        ContextHelper.application = WeakReference(application)
        application.registerActivityLifecycleCallbacks(createActivityLifecycleCallbacks())
    }

    @JvmStatic
    fun setApplication(context: Application?) {
        if (context == null) {
            application = null
            return
        }

        application = WeakReference(context)
    }

    @JvmStatic
    fun setContext(context: Context?) {
        if (context == null) {
            ContextHelper.context = null
            return
        }

        ContextHelper.context = WeakReference(context)
    }

    @JvmStatic
    fun getApplication(): Application? = application?.get() as Application

    @JvmStatic
    fun getContext(): Context? = context?.get() as Context

    @JvmStatic
    fun getFragmentActivity(): FragmentActivity? = context?.get() as FragmentActivity

    @JvmStatic
    fun getAppCompatActivity(): AppCompatActivity? = context?.get() as AppCompatActivity

    @JvmStatic
    fun getActivity(): Activity? = context?.get() as Activity

    val isRunning = AtomicBoolean(false)

    private fun createActivityLifecycleCallbacks(): Application.ActivityLifecycleCallbacks {
        return object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                setContext(activity)
                isRunning.set(true)
            }

            override fun onActivityStarted(activity: Activity) {
                setContext(activity)
                isRunning.set(true)
            }

            override fun onActivityResumed(activity: Activity) {
                setContext(activity)
                isRunning.set(true)
            }

            override fun onActivityPaused(activity: Activity) {
                isRunning.set(false)
            }

            override fun onActivityStopped(activity: Activity) {
                isRunning.set(false)
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {

            }

            override fun onActivityDestroyed(activity: Activity) {
                isRunning.set(false)
            }
        }
    }

    @JvmStatic
    fun onTerminate() {

        context?.clear()
        context = null

        application?.clear()
        application = null

        isRunning.set(false)
    }
}
