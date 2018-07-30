package com.blinkist.easylibrary.di

import android.app.Application
import android.content.Context
import com.blinkist.easylibrary.library.di.LibraryComponent
import com.blinkist.easylibrary.library.di.LibraryModule
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, RetrofitModule::class, DatabaseModule::class])
interface ApplicationComponent {

    fun plusLibraryComponent(): LibraryComponent
}

@Module
class ApplicationModule(private val application: Application) {

    @Provides
    fun provideApplicationContext(): Context = application.applicationContext
}
