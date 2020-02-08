package com.blinkist.easylibrary.di

import com.blinkist.easylibrary.network.di.BaseUrlModule.BaseUrl
import com.blinkist.easylibrary.network.di.MoshiModule
import com.blinkist.easylibrary.network.di.OkHttpModule
import com.blinkist.easylibrary.network.di.RetrofitModule
import dagger.Module
import dagger.Provides
import io.appflate.restmock.RESTMockServer

@Module(includes = [OkHttpModule::class, BaseUrlTestModule::class, RetrofitModule::class, MoshiModule::class])
object NetworkTestModule

@Module
object BaseUrlTestModule {

  @Provides @BaseUrl
  fun provideBaseUrl(): String = RESTMockServer.getUrl()
}
