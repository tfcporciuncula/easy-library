package com.blinkist.easylibrary.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.blinkist.easylibrary.model.Book

@Database(entities = [Book::class], version = 1)
abstract class EasyLibraryDatabase : RoomDatabase() {

    abstract fun bookDao(): BookDao
}
