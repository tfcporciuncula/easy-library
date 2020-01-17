package com.blinkist.easylibrary.features.library

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.blinkist.easylibrary.model.newBook
import com.blinkist.easylibrary.model.newWeekSection
import com.blinkist.easylibrary.model.repositories.BookRepository
import com.blinkist.easylibrary.test.CoroutineRule
import com.blinkist.easylibrary.test.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import com.tfcporciuncula.flow.Preference
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class LibraryViewModelTest {

  @get:Rule val liveDataRule = InstantTaskExecutorRule()
  @get:Rule val coroutineRule = CoroutineRule(TestCoroutineDispatcher())

  private val sortOrderChangeDelay = 100L

  @Mock private lateinit var bookRepository: BookRepository
  @Mock private lateinit var bookGrouper: BookGrouper
  @Mock private lateinit var sortOrderPreference: Preference<LibrarySortOrder>

  private lateinit var viewModel: LibraryViewModel
  private val viewModelState get() = viewModel.select { this }.getOrAwaitValue()

  @Before fun setup() {
    given(bookRepository.books()).willReturn(flowOf(emptyList()))
    given(sortOrderPreference.asFlow()).willReturn(flowOf(LibrarySortOrder.DEFAULT))
  }

  @Test fun `should update state with library items received after initialization`() {
    val books = listOf(newBook(id = 11), newBook(id = 22))
    given(bookRepository.books()).willReturn(flowOf(books))

    val libraryItems = listOf(newWeekSection()) + books
    given(bookGrouper.groupBooksByWeek(books, LibrarySortOrder.DEFAULT)).willReturn(libraryItems)

    initViewModel()

    assertThat(viewModelState.libraryItems).isEqualTo(libraryItems)
  }

  @Test fun `should emit snackbar event if updating books fails`() {
    initViewModel()

    runBlocking { given(bookRepository.updateBooks()).will { throw IOException() } }

    viewModel.updateBooks()

    assertThat(viewModelState.snackbarEvent).isNotNull()
  }

  @Test fun `should not emit snackbar event if updating books succeeds`() {
    initViewModel()
    viewModel.updateBooks()

    assertThat(viewModelState.snackbarEvent).isNull()
  }

  @Test fun `should update sort order when ascending sort option is clicked`() {
    initViewModel()

    viewModel.onArrangeByAscendingClicked()

    verify(sortOrderPreference).set(LibrarySortOrder.ASCENDING)
  }

  @Test fun `should update sort order when descending sort option is clicked`() {
    initViewModel()

    viewModel.onArrangeByDescendingClicked()

    verify(sortOrderPreference).set(LibrarySortOrder.DESCENDING)
  }

  @Test fun `should update library items when sort option changes`() {
    givenSortOptionWillChange()

    val books = listOf(newBook(id = 11), newBook(id = 22))
    val booksReversed = books.reversed()
    given(bookRepository.books()).willReturn(flowOf(books))

    val libraryItems = listOf(newWeekSection()) + books
    val libraryItemsReversed = listOf(newWeekSection()) + booksReversed
    given(bookGrouper.groupBooksByWeek(books, LibrarySortOrder.DESCENDING)).willReturn(libraryItems)
    given(bookGrouper.groupBooksByWeek(books, LibrarySortOrder.ASCENDING)).willReturn(libraryItemsReversed)

    initViewModel()

    assertThat(viewModelState.libraryItems).isEqualTo(libraryItems)
    waitForSortOrderChange()
    assertThat(viewModelState.libraryItems).isEqualTo(libraryItemsReversed)
  }

  private fun givenSortOptionWillChange() {
    given(sortOrderPreference.asFlow()).willReturn(
      flow {
        emit(LibrarySortOrder.DESCENDING)
        delay(sortOrderChangeDelay)
        emit(LibrarySortOrder.ASCENDING)
      }
    )
  }

  private fun waitForSortOrderChange() {
    coroutineRule.dispatcher.advanceTimeBy(sortOrderChangeDelay)
  }

  private fun initViewModel() {
    viewModel = LibraryViewModel(bookRepository, bookGrouper, sortOrderPreference)
  }
}
