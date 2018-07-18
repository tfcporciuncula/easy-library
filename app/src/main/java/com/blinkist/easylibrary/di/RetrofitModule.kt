package com.blinkist.easylibrary.di

import com.blinkist.easylibrary.service.BooksService
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
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

@Module
class RetrofitModule {

    @Qualifier
    private annotation class Internal

    @Qualifier
    annotation class BookServiceDateFormat

    @Provides @Internal
    fun provideBaseUrl(): String = RESTMockServer.getUrl()

    @Provides @Internal
    fun provideHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides @Internal
    fun provideConverterFactory(): Converter.Factory {
        return MoshiConverterFactory.create(Moshi.Builder().add(KotlinJsonAdapterFactory()).build())
    }

    @Provides @Internal
    fun provideCallAdapterFactory(): CallAdapter.Factory = RxJava2CallAdapterFactory.create()

    @Provides @Singleton
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

    @Provides @BookServiceDateFormat
    fun provideBooksServiceDateFormat(): DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
}
