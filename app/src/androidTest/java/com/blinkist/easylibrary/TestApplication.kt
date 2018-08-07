package com.blinkist.easylibrary

import com.blinkist.easylibrary.di.ApplicationModule
import com.blinkist.easylibrary.di.DaggerTestComponent
import com.blinkist.easylibrary.di.TestComponent
import io.appflate.restmock.RESTMockServer

class TestApplication : EasyLibraryApplication() {

    override val component: TestComponent
        get() = DaggerTestComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()

    override fun onCreate() {
        super.onCreate()
        RESTMockServer.shutdown()
    }
}
