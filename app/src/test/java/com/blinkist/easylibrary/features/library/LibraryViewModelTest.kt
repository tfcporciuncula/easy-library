package com.blinkist.easylibrary.features.library

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.blinkist.easylibrary.model.newBook
import com.blinkist.easylibrary.model.newWeekSection
import com.blinkist.easylibrary.model.repositories.BookRepository
import com.blinkist.easylibrary.test.CoroutinesRule
import com.google.common.truth.Truth.assertThat
import com.tfcporciuncula.flow.Preference
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
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
  @get:Rule val coroutinesRule = CoroutinesRule()

  @Mock private lateinit var bookRepository: BookRepository
  @Mock private lateinit var bookGrouper: BookGrouper
  @Mock private lateinit var sortOrderPreference: Preference<LibrarySortOrder>

  private lateinit var viewModel: LibraryViewModel
  private val viewModelState get() = viewModel.state().value!!

  @Before fun setup() {
    given(bookRepository.books()).willReturn(flowOf(emptyList()))
    given(sortOrderPreference.asFlow()).willReturn(flowOf(LibrarySortOrder.DEFAULT))
  }

  @Test fun `should have fetched library items in the state`() {
    val books = listOf(newBook(id = 11), newBook(id = 22))
    given(bookRepository.books()).willReturn(flowOf(books))

    val libraryItems = listOf(newWeekSection()) + books
    given(bookGrouper.groupBooksByWeek(books, LibrarySortOrder.DEFAULT)).willReturn(libraryItems)

    initViewModel()

    assertThat(viewModelState.libraryItems).isEqualTo(libraryItems)
  }

  @Test fun `should emit snackbar event if repository call fails`() {
    initViewModel()

    runBlocking { given(bookRepository.updateBooks()).will { throw IOException() } }

    viewModel.updateBooks()

    assertThat(viewModelState.snackbarEvent).isNotNull()
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

  private fun initViewModel() {
    viewModel = LibraryViewModel(bookRepository, bookGrouper, sortOrderPreference)
  }
}
