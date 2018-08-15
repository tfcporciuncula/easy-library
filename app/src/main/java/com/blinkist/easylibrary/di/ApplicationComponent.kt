package com.blinkist.easylibrary.di

import android.content.Context
import com.blinkist.easylibrary.library.di.LibraryComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [RetrofitModule::class, DatabaseModule::class])
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance fun applicationContext(applicationContext: Context): Builder
        fun build(): ApplicationComponent
    }

    fun plusLibraryComponent(): LibraryComponent
}
