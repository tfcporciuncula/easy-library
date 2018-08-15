package com.blinkist.easylibrary.di

import com.blinkist.easylibrary.service.BooksService
import dagger.Module
import dagger.Provides
import io.appflate.restmock.RESTMockServer
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
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
        .client(OkHttpClient.Builder().build())
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(BooksService::class.java)
}

@Module
object ServiceModule {

    @Qualifier
    annotation class BookServiceDateFormat

    @JvmStatic @Provides @BookServiceDateFormat
    fun provideBooksServiceDateFormat(): DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
}
