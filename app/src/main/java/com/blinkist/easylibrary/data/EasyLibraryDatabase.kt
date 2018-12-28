package com.blinkist.easylibrary.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.blinkist.easylibrary.model.Book

@Database(entities = [Book::class], version = 1)
abstract class EasyLibraryDatabase : RoomDatabase() {

  abstract fun bookDao(): BookDao
}
