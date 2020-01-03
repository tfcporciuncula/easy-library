package com.blinkist.easylibrary.di

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

@Module
object OkHttpModule {

  @Provides fun provideOkHttpClient() = OkHttpClient.Builder().build()
}
