package com.blinkist.easylibrary.di

import com.blinkist.easylibrary.service.BooksService
import dagger.Module
import dagger.Provides
import io.appflate.restmock.RESTMockServer
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module(includes = [ServiceModule::class])
object RetrofitTestModule {

  @JvmStatic @Provides @Singleton
  fun provideBooksService(): BooksService = Retrofit.Builder()
    .baseUrl(RESTMockServer.getUrl())
    .addConverterFactory(MoshiConverterFactory.create())
    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
    .build()
    .create(BooksService::class.java)
}
