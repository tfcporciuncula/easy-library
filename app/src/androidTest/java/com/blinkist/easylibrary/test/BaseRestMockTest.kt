package com.blinkist.easylibrary.test

import androidx.test.ext.junit.runners.AndroidJUnit4
import io.appflate.restmock.RESTMockServer
import io.appflate.restmock.RESTMockServerStarter
import io.appflate.restmock.android.AndroidAssetsFileParser
import io.appflate.restmock.android.AndroidLogger
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
abstract class BaseRestMockTest {

  @Before open fun setup() =
    RESTMockServerStarter.startSync(AndroidAssetsFileParser(instrumentationContext), AndroidLogger())

  @After fun tearDown() = RESTMockServer.shutdown()
}
