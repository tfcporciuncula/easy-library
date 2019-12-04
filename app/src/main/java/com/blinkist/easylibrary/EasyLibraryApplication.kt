package com.blinkist.easylibrary

import android.app.Application
import com.blinkist.easylibrary.di.ApplicationComponent
import com.blinkist.easylibrary.di.DaggerApplicationComponent
import com.blinkist.easylibrary.di.DaggerComponentProvider
import com.blinkist.easylibrary.ktx.unsyncLazy
import io.appflate.restmock.RESTMockServer
import io.appflate.restmock.RESTMockServerStarter
import io.appflate.restmock.android.AndroidAssetsFileParser
import io.appflate.restmock.android.AndroidLogger
import io.appflate.restmock.utils.RequestMatchers.pathContains
import timber.log.Timber
import java.util.concurrent.TimeUnit

class EasyLibraryApplication : Application(), DaggerComponentProvider {

  override val component: ApplicationComponent by unsyncLazy {
    DaggerApplicationComponent.factory().create(applicationContext)
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
      .delayBody(TimeUnit.SECONDS, 2)
      .thenReturnFile(200, "books.json")
      .delayBody(TimeUnit.SECONDS, 2)
      .thenReturnFile(200, "onemorebook.json")
      .delayBody(TimeUnit.SECONDS, 2)
      .thenReturnFile(200, "books.json")
      .delayBody(TimeUnit.SECONDS, 2)
      .thenReturnString("this will trigger an error")
      .delayBody(TimeUnit.SECONDS, 2)
      .thenReturnFile(200, "books.json")
  }
}
