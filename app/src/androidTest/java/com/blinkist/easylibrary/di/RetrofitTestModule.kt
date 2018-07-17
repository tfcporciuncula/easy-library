package com.blinkist.easylibrary.di

import io.appflate.restmock.RESTMockServer

class RetrofitTestModule : RetrofitModule() {

    override fun provideBaseUrl(): String = RESTMockServer.getUrl()
}
