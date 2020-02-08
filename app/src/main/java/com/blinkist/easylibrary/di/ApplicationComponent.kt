package com.blinkist.easylibrary.di

import android.content.Context
import com.blinkist.easylibrary.database.di.DatabaseModule
import com.blinkist.easylibrary.network.di.NetworkModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
  modules = [
    DatabaseModule::class,
    NetworkModule::class,
    SharedPreferencesModule::class
  ]
)
interface ApplicationComponent : ApplicationProvisions, ViewModelProvisions {

  @Component.Factory
  interface Factory {
    fun create(@BindsInstance applicationContext: Context): ApplicationComponent
  }
}
