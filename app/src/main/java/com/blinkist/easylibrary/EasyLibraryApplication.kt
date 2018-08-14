package com.blinkist.easylibrary

import android.app.Application
import com.blinkist.easylibrary.di.ApplicationComponent
import com.blinkist.easylibrary.di.ApplicationModule
import com.blinkist.easylibrary.di.DaggerApplicationComponent
import com.blinkist.easylibrary.di.DaggerComponentProvider
import io.appflate.restmock.RESTMockServer
import io.appflate.restmock.RESTMockServerStarter
import io.appflate.restmock.android.AndroidAssetsFileParser
import io.appflate.restmock.android.AndroidLogger
import io.appflate.restmock.utils.RequestMatchers.pathContains
import timber.log.Timber
import java.util.concurrent.TimeUnit

class EasyLibraryApplication : Application(), DaggerComponentProvider {

    override val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

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
            .delay(TimeUnit.SECONDS, 2)
            .thenReturnFile(200, "books.json")
            .delay(TimeUnit.SECONDS, 2)
            .thenReturnFile(200, "onemorebook.json")
            .delay(TimeUnit.SECONDS, 2)
            .thenReturnFile(200, "books.json")
            .delay(TimeUnit.SECONDS, 2)
            .thenReturnString("this will trigger an error")
            .delay(TimeUnit.SECONDS, 2)
            .thenReturnFile(200, "books.json")
    }
}
