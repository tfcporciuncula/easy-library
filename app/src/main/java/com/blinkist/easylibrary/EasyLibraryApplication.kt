package com.blinkist.easylibrary

import android.app.Application
import io.appflate.restmock.RESTMockServer
import io.appflate.restmock.RESTMockServerStarter
import io.appflate.restmock.android.AndroidAssetsFileParser
import io.appflate.restmock.android.AndroidLogger
import io.appflate.restmock.utils.RequestMatchers.pathContains
import timber.log.Timber

class EasyLibraryApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        setupTimber()
        setupMockApi()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    private fun setupMockApi() {
        RESTMockServerStarter.startSync(AndroidAssetsFileParser(this), AndroidLogger());
        RESTMockServer.whenGET(pathContains("books"))
            .thenReturnFile(200, "books.json");
    }
}
