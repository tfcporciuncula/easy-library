package com.blinkist.easylibrary.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.blinkist.easylibrary.model.LocalBook

@Database(entities = [LocalBook::class], version = 1)
abstract class EasyLibraryDatabase : RoomDatabase() {

  abstract fun bookDao(): BookDao
}
