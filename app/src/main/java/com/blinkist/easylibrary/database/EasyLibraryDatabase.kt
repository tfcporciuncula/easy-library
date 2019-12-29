package com.blinkist.easylibrary.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.blinkist.easylibrary.model.local.LocalBook

@Database(entities = [LocalBook::class], version = 1)
@TypeConverters(DatabaseTypeConverters::class)
abstract class EasyLibraryDatabase : RoomDatabase() {

  abstract fun bookDao(): BookDao
}
