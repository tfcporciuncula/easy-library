package com.blinkist.easylibrary.test.rules

import com.blinkist.easylibrary.test.instrumentation.instrumentationContext
import io.appflate.restmock.RESTMockOptions
import io.appflate.restmock.RESTMockServer
import io.appflate.restmock.RESTMockServerStarter
import io.appflate.restmock.android.AndroidAssetsFileParser
import io.appflate.restmock.android.AndroidLogger
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class RestMockRule : TestWatcher() {

  override fun starting(description: Description?) {
    super.starting(description)
    RESTMockServerStarter.startSync(
      AndroidAssetsFileParser(instrumentationContext),
      AndroidLogger(),
      RESTMockOptions.Builder().useHttps(true).build()
    )
  }

  override fun finished(description: Description?) {
    super.finished(description)
    RESTMockServer.shutdown()
  }
}
