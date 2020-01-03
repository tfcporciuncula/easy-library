package com.blinkist.easylibrary.di

import android.content.Context
import com.blinkist.easylibrary.debug.StethoInitializer
import com.blinkist.easylibrary.features.library.LibraryViewModel
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
interface ApplicationComponent {

  @Component.Factory
  interface Factory {
    fun create(@BindsInstance applicationContext: Context): ApplicationComponent
  }

  val stethoInitializer: StethoInitializer
  val libraryViewModel: LibraryViewModel
}
