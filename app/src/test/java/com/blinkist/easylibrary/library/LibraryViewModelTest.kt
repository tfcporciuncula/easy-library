package com.blinkist.easylibrary.library

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import com.blinkist.easylibrary.base.InjectionAwareTest
import com.blinkist.easylibrary.data.BookDao
import com.blinkist.easylibrary.model.Book
import com.blinkist.easylibrary.model.ModelFactory.newBook
import com.blinkist.easylibrary.model.ModelFactory.newWeekSection
import com.blinkist.easylibrary.service.LibraryService
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify
import javax.inject.Inject

class LibraryViewModelTest : InjectionAwareTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var libraryService: LibraryService

    @Inject
    lateinit var bookDao: BookDao

    @Inject
    lateinit var bookGrouper: BookGrouper

    private val viewModel get() = LibraryViewModel(application)

    @Before
    override fun setup() {
        super.setup()
        component.inject(this)
    }

    @Test
    fun testBooks() {
        val books = listOf(newBook(id = 11), newBook(id = 22))
        given(bookDao.booksLive()).willReturn(
            MutableLiveData<List<Book>>().apply { value = books }
        )

        val librariables = listOf(newWeekSection()) + books
        given(bookGrouper.groupBooksByWeek(books, sortByDescending = true)).willReturn(librariables)

        viewModel.books().observeForever {
            assertEquals(librariables, it)
        }
    }

    @Test
    fun testUpdateBooks() {
        val books = listOf(newBook(id = 12), newBook(id = 34))

        given(libraryService.books()).willReturn(Single.just(books))

        viewModel.updateBooks().test().assertNoErrors()
        verify(libraryService).books()
        verify(bookDao).clear()
        verify(bookDao).insert(books)
    }

    @Test
    fun testChangingBooksSortOrder() {
        val viewModel = this.viewModel

        val books = listOf(newBook(id = 10), newBook(id = 20))
        given(bookDao.books()).willReturn(Single.just(books))

        viewModel.booksRearranged().test()
        verify(bookGrouper).groupBooksByWeek(books, sortByDescending = false)
        viewModel.booksRearranged().test()
        verify(bookGrouper).groupBooksByWeek(books, sortByDescending = true)
    }
}
