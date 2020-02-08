package com.blinkist.easylibrary.test

import io.appflate.restmock.JVMFileParser
import io.appflate.restmock.RESTMockServer
import io.appflate.restmock.RESTMockServerStarter
import io.appflate.restmock.logging.NOOpLogger
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class RestMockRule : TestWatcher() {

  override fun starting(description: Description?) {
    super.starting(description)
    RESTMockServerStarter.startSync(JVMFileParser(), NOOpLogger())
  }

  override fun finished(description: Description?) {
    super.finished(description)
    RESTMockServer.shutdown()
  }
}
