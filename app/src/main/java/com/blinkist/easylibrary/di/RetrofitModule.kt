package com.blinkist.easylibrary.di

import com.blinkist.easylibrary.service.LibraryService
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
import javax.inject.Singleton

@Module
object RetrofitModule {

    @JvmStatic
    @Provides
    fun provideBaseUrl(): String = RESTMockServer.getUrl()

    @JvmStatic
    @Provides
    fun provideHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @JvmStatic
    @Provides
    fun provideConverterFactory(): Converter.Factory {
        return MoshiConverterFactory.create(Moshi.Builder().add(KotlinJsonAdapterFactory()).build())
    }

    @JvmStatic
    @Provides
    fun provideCallAdapterFactory(): CallAdapter.Factory = RxJava2CallAdapterFactory.create()

    @JvmStatic
    @Provides
    @Singleton
    fun provideBooksService(
        baseUrl: String,
        httpClient: OkHttpClient,
        converterFactory: Converter.Factory,
        callAdapterFactory: CallAdapter.Factory
    ): LibraryService = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(httpClient)
        .addConverterFactory(converterFactory)
        .addCallAdapterFactory(callAdapterFactory)
        .build()
        .create(LibraryService::class.java)
}
