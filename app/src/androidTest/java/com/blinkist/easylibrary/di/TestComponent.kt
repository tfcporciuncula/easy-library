package com.blinkist.easylibrary.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, RetrofitTestModule::class, DatabaseTestModule::class])
interface TestComponent : ApplicationComponent
