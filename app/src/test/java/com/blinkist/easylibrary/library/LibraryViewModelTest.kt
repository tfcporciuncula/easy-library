package com.blinkist.easylibrary.library

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import com.blinkist.easylibrary.base.BaseTest
import com.blinkist.easylibrary.data.BookDao
import com.blinkist.easylibrary.model.Book
import com.blinkist.easylibrary.model.ModelFactory.newBook
import com.blinkist.easylibrary.model.WeekSection
import com.blinkist.easylibrary.service.LibraryService
import io.reactivex.Single
import junit.framework.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify
import javax.inject.Inject

class LibraryViewModelTest : BaseTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var libraryService: LibraryService

    @Inject
    lateinit var bookDao: BookDao

    private val viewModel get() = LibraryViewModel(application)

    @Before
    override fun setup() {
        super.setup()
        component.inject(this)
    }

    @Test
    fun testBooksFromTheSameWeek() {
        val booksInTheSameWeek = listOf(
            newBook(publishedDate = "2018-04-28"),
            newBook(publishedDate = "2018-04-27")
        )

        given(bookDao.booksLive()).willReturn(
            MutableLiveData<List<Book>>().apply { value = booksInTheSameWeek }
        )

        viewModel.books().observeForever { librariables ->
            librariables?.let {
                assertTrue(it[0] is WeekSection)
                assertEquals(booksInTheSameWeek[0], it[1] as Book)
                assertEquals(booksInTheSameWeek[1], it[2] as Book)
            } ?: fail()
        }
    }

    @Test
    fun testBooksFromDifferentWeek() {
        val booksInTheSameWeek = listOf(
            newBook(publishedDate = "2018-04-28"),
            newBook(publishedDate = "2017-04-27")
        )

        given(bookDao.booksLive()).willReturn(
            MutableLiveData<List<Book>>().apply { value = booksInTheSameWeek }
        )

        viewModel.books().observeForever { librariables ->
            librariables?.let {
                assertTrue(it[0] is WeekSection)
                assertEquals(booksInTheSameWeek[0], it[1] as Book)
                assertTrue(it[2] is WeekSection)
                assertEquals(booksInTheSameWeek[1], it[3] as Book)
            } ?: fail()
        }
    }

    @Test
    fun testChangingBooksSortOrder() {
        val books = listOf(
            newBook(publishedDate = "2018-04-03"),
            newBook(publishedDate = "2018-04-04")
        )

        given(bookDao.books()).willReturn(Single.just(books))

        val viewModel = this.viewModel
        val sortedBooks1 =
            viewModel.booksSortedDifferently().test().values().first().filter { it is Book }
        val sortedBooks2 =
            viewModel.booksSortedDifferently().test().values().first().filter { it is Book }

        assertEquals(books, sortedBooks1)
        assertEquals(books.reversed(), sortedBooks2)
    }

    @Test
    fun testUpdateBooks() {
        val books = listOf(
            newBook(title = "book1"),
            newBook(title = "book2")
        )

        given(libraryService.books()).willReturn(Single.just(books))

        viewModel.updateBooks().test().assertNoErrors()
        verify(libraryService).books()
        verify(bookDao).clear()
        verify(bookDao).insert(books)
    }
}
