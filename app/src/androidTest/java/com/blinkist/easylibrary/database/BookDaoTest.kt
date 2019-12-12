package com.blinkist.easylibrary.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.blinkist.easylibrary.model.local.LocalBook
import com.blinkist.easylibrary.model.newBook
import com.blinkist.easylibrary.test.instrumentationContext
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BookDaoTest {

  @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()

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
    val books = listOf(newBook(id = 1), newBook(id = 2))

    bookDao.insert(books)

    bookDao.books().observeForever { assertThat(it).isEqualTo(books) }
  }

  @Test fun testReplace() {
    val book = newBook(id = 20)

    bookDao.insert(listOf(book))
    val updatedBook = book.copy(title = "updated book")
    bookDao.insert(listOf(updatedBook))

    bookDao.books().observeForever { assertThat(it).isEqualTo(listOf(updatedBook)) }
  }

  @Test fun testClear() {
    val books = listOf(newBook(id = 123), newBook(id = 456))

    bookDao.insert(books)
    bookDao.clear()

    bookDao.books().observeForever { assertThat(it).isEqualTo(emptyList<LocalBook>()) }
  }
}
