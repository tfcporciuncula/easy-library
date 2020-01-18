package com.blinkist.easylibrary

import android.app.Application
import com.blinkist.easylibrary.di.DaggerComponentProvider
import com.blinkist.easylibrary.di.DaggerTestComponent
import com.jakewharton.threetenabp.AndroidThreeTen
import timber.log.Timber

class TestApplication : Application(), DaggerComponentProvider {

  override val component get() = DaggerTestComponent.factory().create(applicationContext)

  override fun onCreate() {
    super.onCreate()
    initTimber()
    initThreeTen()
  }

  private fun initTimber() = Timber.plant(Timber.DebugTree())

  private fun initThreeTen() = AndroidThreeTen.init(this)
}
