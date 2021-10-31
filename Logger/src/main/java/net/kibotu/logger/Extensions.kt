@file:JvmName("Extensions")

package net.kibotu.logger

import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.github.florent37.application.provider.ActivityProvider.currentActivity
import com.google.android.material.snackbar.Snackbar


val Any.TAG: String
    get() = this::class.java.simpleName.apply { substring(0, 23.coerceAtMost(this.length)) }

val Collection<*>?.isEmpty
    get() = this == null || this.isEmpty()

internal val getContentRoot: View?
    get() = currentActivity
        ?.window
        ?.decorView
        ?.findViewById(android.R.id.content)

fun Snackbar.withTextColor(color: Int): Snackbar {
    view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).setTextColor(color)
    return this
}

fun snack(message: String, actionTitle: String? = null, action: (() -> Unit)? = null) {
    with(Snackbar.make(getContentRoot ?: return, message, Snackbar.LENGTH_LONG)) {
        withTextColor(Color.WHITE)
        setAction(actionTitle) {
            action?.invoke()
        }
        show()
    }
}