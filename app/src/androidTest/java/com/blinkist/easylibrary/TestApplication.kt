package com.blinkist.easylibrary

import android.support.test.InstrumentationRegistry
import com.blinkist.easylibrary.di.ApplicationModule
import com.blinkist.easylibrary.di.DaggerTestComponent
import com.blinkist.easylibrary.di.TestComponent
import io.appflate.restmock.RESTMockServerStarter
import io.appflate.restmock.android.AndroidAssetsFileParser
import io.appflate.restmock.android.AndroidLogger

class TestApplication : EasyLibraryApplication() {

    override val component: TestComponent
        get() = DaggerTestComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()

    override fun setupMockApi() {
        RESTMockServerStarter.startSync(AndroidAssetsFileParser(InstrumentationRegistry.getContext()), AndroidLogger())
    }
}
