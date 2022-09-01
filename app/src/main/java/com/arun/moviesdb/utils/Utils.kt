package com.arun.moviesdb.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

object Utils {
    fun checkInternetAvailability(context: Context): Boolean {
        return try {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            if (cm != null) {
                if (Build.VERSION.SDK_INT < 23) {
                    val ni = cm.activeNetworkInfo
                    if (ni != null) {
                        return ni.isConnected && (ni.type == ConnectivityManager.TYPE_WIFI
                                || ni.type == ConnectivityManager.TYPE_MOBILE)
                    }
                } else {
                    val n = cm.activeNetwork
                    return if (n != null) {
                        val nc = cm.getNetworkCapabilities(n)
                        (nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                                || nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                                || nc.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                                || nc.hasTransport(NetworkCapabilities.TRANSPORT_VPN))
                    } else {
                        false
                    }
                }
            }
            true
        } catch (e: java.lang.Exception) {
            true
        }
    }
}