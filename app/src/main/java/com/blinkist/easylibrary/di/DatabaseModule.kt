package com.blinkist.easylibrary.di

import android.arch.persistence.room.Room
import android.content.Context
import com.blinkist.easylibrary.data.EasyLibraryDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ApplicationModule::class])
class DatabaseModule {

    @Provides @Singleton
    fun provideDatabase(context: Context): EasyLibraryDatabase {
        return Room.databaseBuilder(context, EasyLibraryDatabase::class.java, "easy-library").build()
    }

    @Provides
    fun provideBookDao(database: EasyLibraryDatabase) = database.bookDao()
}
