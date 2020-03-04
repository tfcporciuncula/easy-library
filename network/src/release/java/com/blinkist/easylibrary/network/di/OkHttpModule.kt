package com.blinkist.easylibrary.network.di

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

@Module
object OkHttpModule {

  @Provides fun provideOkHttpClient(okHttpClientBuilder: OkHttpClient.Builder) = okHttpClientBuilder.build()
}
