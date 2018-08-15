package com.blinkist.easylibrary.di

import com.blinkist.easylibrary.service.BooksService
import dagger.Module
import dagger.Provides
import io.appflate.restmock.RESTMockServer
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module(includes = [ServiceModule::class])
object RetrofitTestModule {

    @JvmStatic @Provides @Singleton
    fun provideBooksService(): BooksService = Retrofit.Builder()
        .baseUrl(RESTMockServer.getUrl())
        .client(OkHttpClient.Builder().build())
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(BooksService::class.java)
}
