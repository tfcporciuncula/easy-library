package com.blinkist.easylibrary.network.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

@Module
object OkHttpModule {

  @Provides fun provideOkHttpClient(okHttpClientBuilder: OkHttpClient.Builder) = okHttpClientBuilder
    .addInterceptor(
      HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }
    )
    .addNetworkInterceptor(StethoInterceptor())
    .build()
}
