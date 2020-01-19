package com.blinkist.easylibrary.system

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_ETHERNET
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import android.os.Build
import javax.inject.Inject

class NetworkChecker @Inject constructor(context: Context) {

  private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

  fun isOnline(): Boolean =
    if (Build.VERSION.SDK_INT >= 23) {
      connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork).hasInternetConnection()
    } else {
      connectivityManager.activeNetworkInfo?.isConnectedOrConnecting ?: false
    }

  fun isOffline() = !isOnline()

  private fun NetworkCapabilities?.hasInternetConnection() = this?.let {
    hasTransport(TRANSPORT_WIFI) || hasTransport(TRANSPORT_CELLULAR) || hasTransport(TRANSPORT_ETHERNET)
  } ?: false
}
