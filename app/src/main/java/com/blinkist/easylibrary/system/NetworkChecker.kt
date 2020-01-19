package com.blinkist.easylibrary.system

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import javax.inject.Inject

class NetworkChecker @Inject constructor(context: Context) {

  private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

  fun isOnline(): Boolean =
    if (Build.VERSION.SDK_INT >= 23) {
      connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.let {
        it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
            it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
            it.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
      } ?: false
    } else {
      connectivityManager.activeNetworkInfo?.isConnectedOrConnecting ?: false
    }

  fun isOffline() = !isOnline()
}
