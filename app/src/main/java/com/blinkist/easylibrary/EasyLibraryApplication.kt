package com.blinkist.easylibrary

import android.app.Application
import timber.log.Timber

class EasyLibraryApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}
