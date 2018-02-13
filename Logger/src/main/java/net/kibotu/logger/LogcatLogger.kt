package net.kibotu.logger

import android.os.Handler
import android.os.Looper
import android.support.design.widget.Snackbar
import android.text.TextUtils.isEmpty
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import net.kibotu.ContextHelper.getActivity
import net.kibotu.ContextHelper.getContext

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */

class LogcatLogger : ILogger {

    override fun debug(tag: String, message: String) {
        Log.d(tag, message)
    }

    override fun verbose(tag: String, message: String) {
        Log.v(tag, message)
    }

    override fun information(tag: String, message: String) {
        Log.i(tag, message)
    }

    override fun warning(tag: String, message: String) {
        Log.w(tag, message)
    }

    override fun error(tag: String, message: String) {
        Log.e(tag, message)
    }

    override fun exception(throwable: Throwable) {
        throwable.printStackTrace()
    }

    override fun toast(message: String) {
        if (isEmpty(message))
            return

        Handler(Looper.getMainLooper()).post {
            val context = getContext()
            if (context == null) {
                debug(LogcatLogger::class.java.simpleName, message)
                return@post
            }
            val toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
            toast.setGravity(Gravity.BOTTOM, 0, 100)
            val text = (toast.view as ViewGroup).getChildAt(0) as TextView
            text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
            toast.show()
        }
    }

    override fun snackbar(message: String) {
        if (isEmpty(message))
            return

        Handler(Looper.getMainLooper()).post {
            val contentRoot = getContentRoot()
            if (contentRoot == null) {
                toast(message)
                return@post
            }
            val snackbar = Snackbar.make(contentRoot, message, Snackbar.LENGTH_SHORT)
            val text: TextView = snackbar.view.findViewById(android.support.design.R.id.snackbar_text)
            text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
            snackbar.show()
        }
    }

    private fun getContentRoot(): View? {
        return getActivity()
                ?.window
                ?.decorView
                ?.findViewById(android.R.id.content)
    }
}
