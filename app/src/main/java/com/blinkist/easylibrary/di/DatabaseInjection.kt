package com.blinkist.easylibrary.di

import android.content.Context
import androidx.room.Room
import com.blinkist.easylibrary.data.EasyLibraryDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [DaoModule::class])
object DatabaseModule {

  @Provides @Singleton
  fun provideDatabase(context: Context): EasyLibraryDatabase {
    return Room.databaseBuilder(context, EasyLibraryDatabase::class.java, "easy-library").build()
  }
}

@Module
object DaoModule {

  @Provides fun provideBookDao(database: EasyLibraryDatabase) = database.bookDao()
}
