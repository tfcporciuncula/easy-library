package com.blinkist.easylibrary

import android.support.test.InstrumentationRegistry
import com.blinkist.easylibrary.di.*
import io.appflate.restmock.RESTMockServerStarter
import io.appflate.restmock.android.AndroidAssetsFileParser
import io.appflate.restmock.android.AndroidLogger

class TestApplication : EasyLibraryApplication() {

    override val component: ApplicationComponent
        get() = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .retrofitModule(RetrofitTestModule())
            .databaseModule(DatabaseTestModule())
            .build()

    override fun setupMockApi() {
        RESTMockServerStarter.startSync(AndroidAssetsFileParser(InstrumentationRegistry.getContext()), AndroidLogger())
    }
}
