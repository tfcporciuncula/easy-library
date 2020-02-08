package com.blinkist.easylibrary.di

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
  modules = [
    RetrofitTestModule::class,
    DatabaseTestModule::class,
    SharedPreferencesModule::class
  ]
)
interface TestComponent : ApplicationComponent {

  @Component.Factory
  interface Factory : ApplicationComponent.Factory
}
