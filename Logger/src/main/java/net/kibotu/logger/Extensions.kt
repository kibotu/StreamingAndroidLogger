@file:JvmName("Extensions")

package net.kibotu.logger

import android.view.View
import net.kibotu.ContextHelper


val Any.TAG: String
    get () = this::class.java.simpleName

val Collection<*>?.isEmpty
    get() = this == null || this.isEmpty()

internal val getContentRoot: View?
    get() = ContextHelper.getActivity()
        ?.window
        ?.decorView
        ?.findViewById(android.R.id.content)