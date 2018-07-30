package com.blinkist.easylibrary.base

import android.support.test.InstrumentationRegistry
import com.blinkist.easylibrary.TestApplication
import io.appflate.restmock.RESTMockServer
import org.junit.Before

open class BaseInstrumentationTest {

    val component get() = (InstrumentationRegistry.getTargetContext().applicationContext as TestApplication).component

    @Before
    open fun setup() = RESTMockServer.reset()
}
