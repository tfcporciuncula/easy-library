package com.blinkist.easylibrary.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitTestModule::class, DatabaseTestModule::class])
interface TestComponent : ApplicationComponent {

    @Component.Builder
    interface Builder : ApplicationComponent.Builder
}
