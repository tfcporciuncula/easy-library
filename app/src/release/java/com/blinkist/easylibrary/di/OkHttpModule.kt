package com.blinkist.easylibrary.di

import dagger.Module
import dagger.Provides
import io.appflate.restmock.RESTMockServer
import okhttp3.OkHttpClient

@Module
object OkHttpModule {

  @Provides fun provideOkHttpClient() = OkHttpClient.Builder()
    .sslSocketFactory(RESTMockServer.getSSLSocketFactory(), RESTMockServer.getTrustManager())
    .build()
}
