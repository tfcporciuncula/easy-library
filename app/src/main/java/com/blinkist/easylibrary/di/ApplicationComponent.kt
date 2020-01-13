package com.blinkist.easylibrary.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
  modules = [
    RetrofitModule::class,
    DatabaseModule::class,
    SharedPreferencesModule::class
  ]
)
interface ApplicationComponent : ApplicationProvisions, ViewModelProvisions {

  @Component.Factory
  interface Factory {
    fun create(@BindsInstance applicationContext: Context): ApplicationComponent
  }
}
