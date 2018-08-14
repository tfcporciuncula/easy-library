package com.blinkist.easylibrary.di

import android.app.Application

interface DaggerComponentProvider {

    val component: ApplicationComponent
}

val Application.component get() = (this as DaggerComponentProvider).component
