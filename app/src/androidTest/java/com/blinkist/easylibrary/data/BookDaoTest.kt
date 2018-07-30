package com.blinkist.easylibrary.data

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.support.test.runner.AndroidJUnit4
import com.blinkist.easylibrary.base.BaseInstrumentationTest
import com.blinkist.easylibrary.model.Book
import com.blinkist.easylibrary.model.ModelFactory.newBook
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class BookDaoTest : BaseInstrumentationTest() {

    @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject lateinit var database: EasyLibraryDatabase
    @Inject lateinit var bookDao: BookDao

    @Before override fun setup() = component.inject(this)

    @After fun tearDown() = database.close()

    @Test fun testInsert() {
        val books = listOf(newBook(id = 1), newBook(id = 2))

        bookDao.insert(books)

        bookDao.books().observeForever { assertThat(it).isEqualTo(books) }
    }

    @Test fun testReplace() {
        val book = newBook(id = 20)

        bookDao.insert(listOf(book))
        val updatedBook = book.copy(title = "updatedBook1")
        bookDao.insert(listOf(updatedBook))

        bookDao.books().observeForever { assertThat(it).isEqualTo(listOf(updatedBook)) }
    }

    @Test fun testClear() {
        val books = listOf(newBook(id = 123), newBook(id = 456))

        bookDao.insert(books)
        bookDao.clear()

        bookDao.books().observeForever { assertThat(it).isEqualTo(emptyList<Book>()) }
    }
}
