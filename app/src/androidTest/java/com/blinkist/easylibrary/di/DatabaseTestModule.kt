package com.blinkist.easylibrary.di

import androidx.room.Room
import android.content.Context
import com.blinkist.easylibrary.data.EasyLibraryDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [DaoModule::class])
object DatabaseTestModule {

    @JvmStatic @Provides @Singleton
    fun provideDatabase(context: Context): EasyLibraryDatabase {
        return Room.inMemoryDatabaseBuilder(context, EasyLibraryDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }
}
