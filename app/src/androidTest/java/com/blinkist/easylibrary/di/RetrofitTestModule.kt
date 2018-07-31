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
import javax.inject.Singleton

@Module(includes = [ServiceModule::class])
object RetrofitTestModule {

    @JvmStatic @Provides
    fun provideBaseUrl(): String = RESTMockServer.getUrl()

    @JvmStatic @Provides
    fun provideHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @JvmStatic @Provides
    fun provideConverterFactory(): Converter.Factory = MoshiConverterFactory.create()

    @JvmStatic @Provides
    fun provideCallAdapterFactory(): CallAdapter.Factory = RxJava2CallAdapterFactory.create()

    @JvmStatic @Provides @Singleton
    fun provideBooksService(
        baseUrl: String,
        httpClient: OkHttpClient,
        converterFactory: Converter.Factory,
        callAdapterFactory: CallAdapter.Factory
    ): BooksService = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(httpClient)
        .addConverterFactory(converterFactory)
        .addCallAdapterFactory(callAdapterFactory)
        .build()
        .create(BooksService::class.java)
}
