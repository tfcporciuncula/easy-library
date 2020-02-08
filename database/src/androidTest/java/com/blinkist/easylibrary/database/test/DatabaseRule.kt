package com.blinkist.easylibrary.database.test

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.blinkist.easylibrary.database.EasyLibraryDatabase
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class DatabaseRule : TestWatcher() {

  lateinit var database: EasyLibraryDatabase

  override fun starting(description: Description?) {
    super.starting(description)
    database = Room
      .inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), EasyLibraryDatabase::class.java)
      .allowMainThreadQueries()
      .build()
  }

  override fun finished(description: Description?) {
    super.finished(description)
    database.close()
  }
}
