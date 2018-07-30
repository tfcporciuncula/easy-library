package com.blinkist.easylibrary.di

import android.arch.persistence.room.Room
import android.content.Context
import com.blinkist.easylibrary.data.EasyLibraryDatabase
import dagger.Module
import dagger.Provides

@Module(includes = [DaoModule::class])
object DatabaseTestModule {

    @JvmStatic @Provides
    fun provideDatabase(context: Context): EasyLibraryDatabase {
        return Room.inMemoryDatabaseBuilder(context, EasyLibraryDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }
}
