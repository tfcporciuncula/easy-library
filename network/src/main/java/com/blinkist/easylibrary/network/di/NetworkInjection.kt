package com.blinkist.easylibrary.network.di

import com.blinkist.easylibrary.network.BooksService
import com.blinkist.easylibrary.network.LocalDateAdapter
import com.blinkist.easylibrary.network.OffsetDateTimeAdapter
import com.blinkist.easylibrary.network.di.BaseUrlModule.BaseUrl
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import io.appflate.restmock.RESTMockServer
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module(
  includes = [
    BaseUrlModule::class,
    OkHttpModule::class,
    MoshiModule::class,
    RetrofitModule::class
  ]
)
object NetworkModule

@Module
object RetrofitModule {

  @Provides @Singleton
  fun provideBooksService(
    @BaseUrl baseUrl: String,
    okHttpClient: OkHttpClient,
    moshi: Moshi
  ): BooksService = Retrofit.Builder()
    .baseUrl(baseUrl)
    .client(okHttpClient)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()
    .create(BooksService::class.java)
}

@Module
object BaseUrlModule {

  @Qualifier annotation class BaseUrl

  @Provides @BaseUrl
  fun provideBaseUrl(): String = RESTMockServer.getUrl()
}

@Module
object MoshiModule {

  @Provides fun provideMoshi(): Moshi = Moshi.Builder()
    .add(OffsetDateTimeAdapter())
    .add(LocalDateAdapter())
    .build()
}
