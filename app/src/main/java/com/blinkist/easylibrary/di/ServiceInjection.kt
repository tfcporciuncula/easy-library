package com.blinkist.easylibrary.di

import com.blinkist.easylibrary.service.BooksService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import io.appflate.restmock.RESTMockServer
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Qualifier
import javax.inject.Singleton

@Module(includes = [ServiceModule::class])
object RetrofitModule {

  @JvmStatic @Provides @Singleton
  fun provideBooksService(): BooksService = Retrofit.Builder()
    .baseUrl(RESTMockServer.getUrl())
    .addConverterFactory(MoshiConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()
    .create(BooksService::class.java)
}

@Module
object ServiceModule {

  @Qualifier
  annotation class ServiceDateFormat

  @JvmStatic @Provides @ServiceDateFormat
  fun provideBooksServiceDateFormat(): DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
}
