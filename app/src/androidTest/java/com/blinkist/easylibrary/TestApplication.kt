package com.blinkist.easylibrary

import android.app.Application
import com.blinkist.easylibrary.di.ApplicationModule
import com.blinkist.easylibrary.di.DaggerComponentProvider
import com.blinkist.easylibrary.di.DaggerTestComponent
import com.blinkist.easylibrary.di.TestComponent

class TestApplication : Application(), DaggerComponentProvider {

    override val component: TestComponent
        get() = DaggerTestComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
}
