package com.blinkist.easylibrary.di

import android.content.Context
import com.blinkist.easylibrary.features.library.LibraryViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitModule::class, DatabaseModule::class, SharedPreferencesModule::class])
interface ApplicationComponent {

  @Component.Builder
  interface Builder {
    @BindsInstance fun applicationContext(applicationContext: Context): Builder
    fun build(): ApplicationComponent
  }

  val libraryViewModel: LibraryViewModel
}
