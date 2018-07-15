package com.blinkist.easylibrary.di

import android.app.Application
import android.content.Context
import com.blinkist.easylibrary.library.LibraryActivity
import com.blinkist.easylibrary.library.di.LibraryModule
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, RetrofitModule::class, DatabaseModule::class, LibraryModule::class])
interface ApplicationComponent {

    fun inject(activity: LibraryActivity)
}

@Module
class ApplicationModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = application.applicationContext
}
