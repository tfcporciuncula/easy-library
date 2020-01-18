package com.blinkist.easylibrary.service

import android.content.Context
import io.appflate.restmock.RESTMockServer
import io.appflate.restmock.RESTMockServerStarter
import io.appflate.restmock.android.AndroidAssetsFileParser
import io.appflate.restmock.android.AndroidLogger
import io.appflate.restmock.utils.RequestMatchers
import java.util.concurrent.TimeUnit

object MockServer {

  fun setupAndStart(context: Context) {
    RESTMockServerStarter.startSync(AndroidAssetsFileParser(context), AndroidLogger())
    RESTMockServer.whenGET(RequestMatchers.pathContains("books"))
      .thenReturnFile(200, "books.json")
      .thenReturnFile(200, "onemorebook.json")
      .thenReturnFile(200, "books.json")
      .thenReturnEmpty(500)
      .thenReturnFile(200, "books.json")
      .delayBody(TimeUnit.SECONDS, 2)
  }
}
