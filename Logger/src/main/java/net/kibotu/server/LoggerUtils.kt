/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */
@file:JvmName("LoggerUtils")

package net.kibotu.server

import android.content.Context
import android.net.wifi.WifiManager
import com.github.florent37.application.provider.application

fun openBrowserMessage(port: Int) = "Open http://$formattedIpAddress:$port in your browser. If website can't be found: make sure device and pc are on the same network segment."

val ipAddress
    get() = (application?.getSystemService(Context.WIFI_SERVICE) as? WifiManager)
        ?.connectionInfo
        ?.ipAddress
        ?: 0

val formattedIpAddress
    get() = with(ipAddress) {
        String.format(
            "%d.%d.%d.%d",
            this and 0xff,
            this shr 8 and 0xff,
            this shr 16 and 0xff,
            this shr 24 and 0xff
        )
    }