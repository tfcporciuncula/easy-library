package com.blinkist.easylibrary.di

import android.content.Context
import androidx.room.Room
import com.blinkist.easylibrary.database.EasyLibraryDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [DaoModule::class])
object DatabaseTestModule {

  @Provides @Singleton
  fun provideDatabase(context: Context) =
    Room.inMemoryDatabaseBuilder(context, EasyLibraryDatabase::class.java)
      .allowMainThreadQueries()
      .build()
}
