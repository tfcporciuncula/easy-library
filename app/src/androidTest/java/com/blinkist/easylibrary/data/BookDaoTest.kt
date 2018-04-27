package com.blinkist.easylibrary.data

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.blinkist.easylibrary.model.Book
import com.blinkist.easylibrary.model.ModelFactory.newBook
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BookDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: EasyLibraryDatabase
    private lateinit var bookDao: BookDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), EasyLibraryDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        bookDao = database.bookDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testInsert() {
        val books = listOf(newBook(id = 1), newBook(id = 2))

        bookDao.insert(books)

        bookDao.books().test().assertValues(books)
        bookDao.booksLive().observeForever { assertEquals(books, it) }
    }

    @Test
    fun testReplace() {
        val book = newBook(id = 20)

        bookDao.insert(book)
        val updatedBook = book.copy(title = "updatedBook1")
        bookDao.insert(updatedBook)

        bookDao.books().test().assertValues(listOf(updatedBook))
        bookDao.booksLive().observeForever { assertEquals(listOf(updatedBook), it) }
    }

    @Test
    fun testDelete() {
        val book1 = newBook(id = 100)
        val book2 = newBook(id = 200)

        bookDao.insert(book1, book2)
        bookDao.delete(book1)

        bookDao.books().test().assertValue(listOf(book2))
        bookDao.booksLive().observeForever { assertEquals(listOf(book2), it) }
    }

    @Test
    fun testClear() {
        val books = listOf(newBook(id = 123), newBook(id = 456))

        bookDao.insert(books)
        bookDao.clear()

        bookDao.books().test().assertValues(emptyList())
        bookDao.booksLive().observeForever { assertEquals(emptyList<Book>(), it) }
    }
}
