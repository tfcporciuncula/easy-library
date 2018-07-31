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

    @Qualifier
    private annotation class Internal

    @JvmStatic @Provides @Internal
    fun provideBaseUrl(): String = RESTMockServer.getUrl()

    @JvmStatic @Provides @Internal
    fun provideHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @JvmStatic @Provides @Internal
    fun provideConverterFactory(): Converter.Factory = MoshiConverterFactory.create()

    @JvmStatic @Provides @Internal
    fun provideCallAdapterFactory(): CallAdapter.Factory = RxJava2CallAdapterFactory.create()

    @JvmStatic @Provides @Singleton
    fun provideBooksService(
        @Internal baseUrl: String,
        @Internal httpClient: OkHttpClient,
        @Internal converterFactory: Converter.Factory,
        @Internal callAdapterFactory: CallAdapter.Factory
    ): BooksService = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(httpClient)
        .addConverterFactory(converterFactory)
        .addCallAdapterFactory(callAdapterFactory)
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
