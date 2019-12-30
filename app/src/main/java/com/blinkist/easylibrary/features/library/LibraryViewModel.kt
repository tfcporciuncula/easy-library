package com.blinkist.easylibrary.features.library

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blinkist.easylibrary.R
import com.blinkist.easylibrary.di.SharedPreferencesModule.LibrarySortOrderPreference
import com.blinkist.easylibrary.features.library.LibraryViewState.SnackbarEvent
import com.blinkist.easylibrary.features.library.LibraryViewState.SortDialogClickedEvent
import com.blinkist.easylibrary.ktx.launchCatching
import com.blinkist.easylibrary.livedata.NonNullMutableLiveData
import com.blinkist.easylibrary.model.presentation.Book
import com.blinkist.easylibrary.model.repositories.BookRepository
import com.tfcporciuncula.flow.Preference
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class LibraryViewModel @Inject constructor(
  private val bookRepository: BookRepository,
  private val bookGrouper: BookGrouper,
  @LibrarySortOrderPreference private val sortOrderPreference: Preference<LibrarySortOrder>
) : ViewModel() {

  private val state = NonNullMutableLiveData(initialValue = LibraryViewState())

  init {
    bookRepository
      .books()
      .combine(sortOrderPreference.asFlow()) { books, sortOrder ->
        state.update {
          copy(libraryItems = bookGrouper.groupBooksByWeek(books, sortOrder), currentSortOrder = sortOrder)
        }
      }
      .launchIn(viewModelScope)

    updateBooks()
  }

  fun state(): LiveData<LibraryViewState> = state

  fun onItemClicked(book: Book) {
    Timber.d("${book.title} was clicked!")
  }

  fun updateBooks() = viewModelScope.launchCatching(
    block = {
      state.update { copy(isLoading = true) }
      bookRepository.updateBooks()
      state.update { copy(isLoading = false) }
    },
    onFailure = ::handleFailure
  )

  private fun handleFailure(throwable: Throwable) {
    val isNetworkFailure = throwable is IOException
    if (!isNetworkFailure) Timber.e(throwable)

    state.update {
      copy(
        isLoading = false,
        snackbarEvent = SnackbarEvent(
          messageResId = if (isNetworkFailure) R.string.network_error_message else R.string.unexpected_error_message
        )
      )
    }
  }

  fun onArrangeByAscendingClicked() = onArrangeBooksClicked(LibrarySortOrder.ASCENDING)

  fun onArrangeByDescendingClicked() = onArrangeBooksClicked(LibrarySortOrder.DESCENDING)

  private fun onArrangeBooksClicked(sortOrder: LibrarySortOrder) {
    sortOrderPreference.set(sortOrder)
    state.update { copy(sortDialogClickedEvent = SortDialogClickedEvent()) }
  }
}
