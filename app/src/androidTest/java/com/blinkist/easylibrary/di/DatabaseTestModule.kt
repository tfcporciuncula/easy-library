package com.blinkist.easylibrary.di

import android.arch.persistence.room.Room
import android.content.Context
import com.blinkist.easylibrary.data.EasyLibraryDatabase

class DatabaseTestModule : DatabaseModule() {

    override fun provideDatabase(context: Context): EasyLibraryDatabase {
        return Room.inMemoryDatabaseBuilder(context, EasyLibraryDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }
}
