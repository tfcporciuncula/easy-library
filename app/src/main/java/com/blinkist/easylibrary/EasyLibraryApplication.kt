package com.blinkist.easylibrary

import android.app.Application
import com.blinkist.easylibrary.di.ApplicationComponent
import com.blinkist.easylibrary.di.DaggerApplicationComponent
import com.blinkist.easylibrary.di.DaggerComponentProvider
import com.blinkist.easylibrary.network.MockServer
import com.blinkist.easylibrary.util.ktx.unsyncLazy
import com.jakewharton.threetenabp.AndroidThreeTen
import timber.log.Timber

class EasyLibraryApplication : Application(), DaggerComponentProvider {

  override val component: ApplicationComponent by unsyncLazy {
    DaggerApplicationComponent.factory().create(applicationContext)
  }

  override fun onCreate() {
    super.onCreate()

    initNightMode()
    initMockServer()
    initTimber()
    initThreeTen()
    initStetho()
  }

  private fun initNightMode() = component.nightThemeManager.initializeNightMode()

  private fun initMockServer() = MockServer.setupAndStart(this)

  private fun initTimber() {
    if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
  }

  private fun initThreeTen() = AndroidThreeTen.init(this)

  private fun initStetho() = component.stethoInitializer.init()
}
