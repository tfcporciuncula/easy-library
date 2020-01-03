package com.blinkist.easylibrary.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

@Module
object OkHttpModule {

  @Provides fun provideOkHttpClient() = OkHttpClient.Builder()
    .addInterceptor(
      HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }
    )
    .addNetworkInterceptor(StethoInterceptor())
    .build()
}
