package com.blinkist.easylibrary.features.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blinkist.easylibrary.R
import com.blinkist.easylibrary.di.SharedPreferencesModule.LibrarySortOrderPreference
import com.blinkist.easylibrary.features.library.LibraryViewState.NavigationEvent
import com.blinkist.easylibrary.features.library.LibraryViewState.SnackbarEvent
import com.blinkist.easylibrary.model.presentation.Book
import com.blinkist.easylibrary.model.repositories.BookRepository
import com.blinkist.easylibrary.network.NetworkChecker
import com.blinkist.easylibrary.util.ktx.launchCatching
import com.blinkist.easylibrary.util.ktx.select
import com.blinkist.easylibrary.util.livedata.NonNullMutableLiveData
import com.tfcporciuncula.flow.Preference
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import timber.log.Timber
import javax.inject.Inject

class LibraryViewModel @Inject constructor(
  private val bookRepository: BookRepository,
  private val bookGrouper: BookGrouper,
  @LibrarySortOrderPreference private val sortOrderPreference: Preference<LibrarySortOrder>,
  private val networkChecker: NetworkChecker
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

  fun <T> select(property: LibraryViewState.() -> T) = state.select(property)

  fun updateBooks() = viewModelScope.launchCatching(
    block = {
      state.update { copy(isLoading = true) }
      bookRepository.updateBooks()
      state.update { copy(isLoading = false) }
    },
    onFailure = ::handleFailure
  )

  private fun handleFailure(throwable: Throwable) {
    val isNetworkFailure = networkChecker.isOffline()
    if (!isNetworkFailure) {
      Timber.e(throwable, "Error updating books -- user is online.")
    } else {
      Timber.i(throwable, "Error updating books -- user is offline.")
    }

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

  private fun onArrangeBooksClicked(sortOrder: LibrarySortOrder) = sortOrderPreference.set(sortOrder)

  fun onBookClicked(book: Book) = state.update { copy(navigationEvent = NavigationEvent.ToWebView(book.url)) }

  fun onSortMenuOptionClicked() = state.update { copy(navigationEvent = NavigationEvent.ToSortOptionDialog()) }

  fun onThemeMenuOptionClicked() {
    // TODO
  }
}
