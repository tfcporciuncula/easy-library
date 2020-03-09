package com.blinkist.easylibrary

import android.app.Application
import com.blinkist.easylibrary.di.DaggerComponentProvider
import com.blinkist.easylibrary.di.TestComponent
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.Dagger
import timber.log.Timber

class TestApplication : Application(), DaggerComponentProvider {

  override val component get() = Dagger.factory(TestComponent.Factory::class.java).create(applicationContext)

  override fun onCreate() {
    super.onCreate()
    initTimber()
    initThreeTen()
  }

  private fun initTimber() = Timber.plant(Timber.DebugTree())

  private fun initThreeTen() = AndroidThreeTen.init(this)
}
