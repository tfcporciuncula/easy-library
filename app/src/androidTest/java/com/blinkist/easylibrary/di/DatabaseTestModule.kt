package com.blinkist.easylibrary.di

import android.arch.persistence.room.Room
import android.content.Context
import android.support.test.InstrumentationRegistry
import com.blinkist.easylibrary.data.EasyLibraryDatabase

class DatabaseTestModule : DatabaseModule() {

    override fun provideDatabase(context: Context): EasyLibraryDatabase {
        return Room.inMemoryDatabaseBuilder(context, EasyLibraryDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    fun buildDatabase() = provideDatabase(InstrumentationRegistry.getContext())
}
