package com.blinkist.easylibrary.network.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import io.appflate.restmock.RESTMockServer
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

@Module
object OkHttpModule {

  @Provides fun provideOkHttpClient() = OkHttpClient.Builder()
    .sslSocketFactory(RESTMockServer.getSSLSocketFactory(), RESTMockServer.getTrustManager())
    .addInterceptor(
      HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }
    )
    .addNetworkInterceptor(StethoInterceptor())
    .build()
}
