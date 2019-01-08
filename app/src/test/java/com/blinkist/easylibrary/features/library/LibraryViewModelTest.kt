package com.blinkist.easylibrary.features.library

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.blinkist.easylibrary.data.BookDao
import com.blinkist.easylibrary.model.*
import com.blinkist.easylibrary.service.BooksService
import com.google.common.truth.Truth.assertThat
import io.reactivex.Single
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

  @Mock private lateinit var booksService: BooksService

  @Mock private lateinit var bookMapper: BookMapper

  @Mock private lateinit var bookDao: BookDao

  @Mock private lateinit var bookGrouper: BookGrouper

  @Mock private lateinit var libraryAdapter: LibraryAdapter

  @Mock private lateinit var sortByDescendingPreference: SortByDescendingPreference

  private val viewModel
    get() = LibraryViewModel(
      booksService,
      bookMapper,
      bookDao,
      bookGrouper,
      sortByDescendingPreference,
      libraryAdapter
    )

  @Test fun testBooks() {
    val books = listOf(newBook(id = 11), newBook(id = 22))
    given(bookDao.books()).willReturn(
      MutableLiveData<List<Book>>().apply { value = books }
    )

    val librariables = listOf(newWeekSection()) + books
    given(bookGrouper.groupBooksByWeek(books, sortByDescending = true)).willReturn(librariables)

    viewModel.state().observeForever {
      assertThat(librariables).isEqualTo(it?.books)
    }
  }

  @Test fun testUpdateBooks() {
    val booksRaw = listOf(newBookRaw(id = 12), newBookRaw(id = 34))
    given(booksService.books()).willReturn(Single.just(booksRaw))
    val books = listOf(newBook(id = 1), newBook(id = 2))
    given(bookMapper.fromRaw(booksRaw)).willReturn(books)

    viewModel.updateBooks()
    verify(booksService).books()
    verify(bookDao).clear()
    verify(bookDao).insert(books)
  }

  @Test fun testRearrangeBooks() {
    val books = listOf(newBook(id = 10), newBook(id = 20))
    given(bookDao.books()).willReturn(
      MutableLiveData<List<Book>>().apply { value = books }
    )

    with(viewModel) {
      rearrangeBooks(sortByDescending = false)
      assertThat(sortByDescending).isFalse()
      verify(bookGrouper).groupBooksByWeek(books, sortByDescending = false)

      rearrangeBooks(sortByDescending = true)
      assertThat(sortByDescending).isTrue()
      verify(bookGrouper).groupBooksByWeek(books, sortByDescending = true)
    }
  }
}
