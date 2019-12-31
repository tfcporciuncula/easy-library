package com.blinkist.easylibrary.database

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.blinkist.easylibrary.model.local.LocalBook
import com.blinkist.easylibrary.model.newLocalBook
import com.blinkist.easylibrary.test.instrumentationContext
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BookDaoTest {

  private lateinit var database: EasyLibraryDatabase
  private lateinit var bookDao: BookDao

  @Before fun setup() {
    database = Room.inMemoryDatabaseBuilder(instrumentationContext, EasyLibraryDatabase::class.java)
      .allowMainThreadQueries()
      .build()
    bookDao = database.bookDao()
  }

  @After fun tearDown() = database.close()

  @Test fun testInsert() {
    val books = listOf(newLocalBook(id = 1), newLocalBook(id = 2))

    runBlocking {
      bookDao.insert(books)

      assertThat(bookDao.books().first()).isEqualTo(books)
    }
  }

  @Test fun testReplace() {
    val book = newLocalBook(id = 20)

    runBlocking {
      bookDao.insert(listOf(book))
      val updatedBook = book.copy(title = "updated book")
      bookDao.insert(listOf(updatedBook))

      assertThat(bookDao.books().first()).isEqualTo(listOf(updatedBook))
    }
  }

  @Test fun testClear() {
    val books = listOf(newLocalBook(id = 123), newLocalBook(id = 456))

    runBlocking {
      bookDao.insert(books)
      bookDao.clear()

      assertThat(bookDao.books().first()).isEqualTo(emptyList<LocalBook>())
    }
  }

  @Test fun testClearAndInsert() {
    val books = listOf(newLocalBook(id = 11), newLocalBook(id = 22))
    val newBooks = listOf(newLocalBook(id = 34), newLocalBook(id = 56))

    runBlocking {
      bookDao.insert(books)
      bookDao.clearAndInsert(newBooks)

      assertThat(bookDao.books().first()).isEqualTo(newBooks)
    }
  }
}
