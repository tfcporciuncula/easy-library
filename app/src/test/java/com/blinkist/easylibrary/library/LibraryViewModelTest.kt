package com.blinkist.easylibrary.library

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import com.blinkist.easylibrary.data.BookDao
import com.blinkist.easylibrary.model.Book
import com.blinkist.easylibrary.model.ModelFactory.newBook
import com.blinkist.easylibrary.model.ModelFactory.newWeekSection
import com.blinkist.easylibrary.service.BooksService
import io.reactivex.Single
import junit.framework.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LibraryViewModelTest {

    @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock lateinit var booksService: BooksService

    @Mock lateinit var bookDao: BookDao

    @Mock lateinit var bookGrouper: BookGrouper

    @Mock lateinit var libraryAdapter: LibraryAdapter

    private val viewModel
        get() = LibraryViewModel(
            booksService,
            bookDao,
            bookGrouper,
            libraryAdapter
        )

    @Test fun testLibrariables() {
        val books = listOf(newBook(id = 11), newBook(id = 22))
        given(bookDao.books()).willReturn(
            MutableLiveData<List<Book>>().apply { value = books }
        )

        val librariables = listOf(newWeekSection()) + books
        given(bookGrouper.groupBooksByWeek(books, sortByDescending = true)).willReturn(librariables)

        viewModel.books().observeForever {
            assertEquals(librariables, it)
        }
    }

    @Test fun testUpdateBooks() {
        val books = listOf(newBook(id = 12), newBook(id = 34))

        given(booksService.books()).willReturn(Single.just(books))

        viewModel.updateBooks().test().assertComplete()
        verify(booksService).books()
        verify(bookDao).clear()
        verify(bookDao).insert(books)
    }

    @Test fun testRearrangeBooks() {
        val books = listOf(newBook(id = 10), newBook(id = 20))
        given(bookDao.books()).willReturn(
            MutableLiveData<List<Book>>().apply { value = books }
        )

        val viewModel = this.viewModel

        viewModel.rearrangeBooks(sortByDescending = false)
        assertFalse(viewModel.sortByDescending)
        verify(bookGrouper).groupBooksByWeek(books, sortByDescending = false)

        viewModel.rearrangeBooks(sortByDescending = true)
        assertTrue(viewModel.sortByDescending)
        verify(bookGrouper).groupBooksByWeek(books, sortByDescending = true)
    }
}
