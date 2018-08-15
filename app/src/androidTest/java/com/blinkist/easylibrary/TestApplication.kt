package com.blinkist.easylibrary

import android.app.Application
import com.blinkist.easylibrary.di.DaggerComponentProvider
import com.blinkist.easylibrary.di.DaggerTestComponent

class TestApplication : Application(), DaggerComponentProvider {

    override val component
        get() = DaggerTestComponent.builder()
            .applicationContext(applicationContext)
            .build()
}
