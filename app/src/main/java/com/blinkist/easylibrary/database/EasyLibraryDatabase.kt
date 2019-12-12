package com.blinkist.easylibrary.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.blinkist.easylibrary.model.local.LocalBook

@Database(entities = [LocalBook::class], version = 1)
abstract class EasyLibraryDatabase : RoomDatabase() {

  abstract fun bookDao(): BookDao
}
