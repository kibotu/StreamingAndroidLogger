/**
 * Created by [Jan Rabe](https://about.me/janrabe).
 */
@file:JvmName("LoggerUtils")

package net.kibotu.server

import android.annotation.SuppressLint
import android.content.Context
import android.net.wifi.WifiManager
import net.kibotu.ContextHelper

@SuppressLint("WifiManagerLeak")
fun getIpAddressLog(port: Int): String {
    val wifiManager = ContextHelper.getApplication()?.getSystemService(Context.WIFI_SERVICE) as? WifiManager
    val ipAddress = wifiManager?.connectionInfo?.ipAddress ?: 0
    @SuppressLint("DefaultLocale") val formattedIpAddress = String.format(
        "%d.%d.%d.%d",
        ipAddress and 0xff,
        ipAddress shr 8 and 0xff,
        ipAddress shr 16 and 0xff,
        ipAddress shr 24 and 0xff
    )
    return "Open http://$formattedIpAddress:$port in your browser. If website can't be found: make sure device and pc are on the same network segment."
}