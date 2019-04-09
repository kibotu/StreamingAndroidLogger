package net.kibotu.server

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */
data class ResponseMessage(val message: String?) {

    private val time: String?

    init {
        time = dateFormat.format(Date())
    }

    companion object {

        @SuppressLint("SimpleDateFormat")
        internal var dateFormat = SimpleDateFormat("yyy-MM-dd HH:mm:ss.SSS")
    }
}