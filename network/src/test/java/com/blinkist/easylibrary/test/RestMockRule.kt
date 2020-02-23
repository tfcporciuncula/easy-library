package com.blinkist.easylibrary.test

import com.blinkist.easylibrary.network.di.MoshiModule
import com.blinkist.easylibrary.network.di.OkHttpModule
import com.blinkist.easylibrary.network.di.RetrofitModule
import io.appflate.restmock.JVMFileParser
import io.appflate.restmock.RESTMockServer
import io.appflate.restmock.RESTMockServerStarter
import io.appflate.restmock.logging.NOOpLogger
import okhttp3.OkHttpClient
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class RestMockRule : TestWatcher() {

  val booksService by lazy(LazyThreadSafetyMode.NONE) {
    RetrofitModule.provideBooksService(
      baseUrl = RESTMockServer.getUrl(),
      okHttpClient = OkHttpClient.Builder().build(),
      moshi = MoshiModule.provideMoshi()
    )
  }

  override fun starting(description: Description?) {
    super.starting(description)
    RESTMockServerStarter.startSync(JVMFileParser(), NOOpLogger())
  }

  override fun finished(description: Description?) {
    super.finished(description)
    RESTMockServer.shutdown()
  }
}
