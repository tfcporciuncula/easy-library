package com.blinkist.easylibrary.di

import com.blinkist.easylibrary.service.BooksService
import dagger.Module
import dagger.Provides
import io.appflate.restmock.RESTMockServer
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module(includes = [ServiceModule::class])
object RetrofitTestModule {

  @Provides @Singleton
  fun provideBooksService(): BooksService = Retrofit.Builder()
    .baseUrl(RESTMockServer.getUrl())
    .addConverterFactory(MoshiConverterFactory.create())
    .build()
    .create(BooksService::class.java)
}
