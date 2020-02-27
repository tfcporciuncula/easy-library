package com.blinkist.easylibrary.network.ktx

import dagger.Lazy
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit

internal fun Retrofit.Builder.lazyCallFactory(lazyCallFactory: Lazy<OkHttpClient>) =
  callFactory(
    object : Call.Factory {
      override fun newCall(request: Request) = lazyCallFactory.get().newCall(request)
    }
  )
