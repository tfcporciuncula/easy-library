package com.blinkist.easylibrary.features.library

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blinkist.easylibrary.di.SharedPreferencesModule.SortByDescending
import com.blinkist.easylibrary.features.library.LibraryViewState.ErrorEvent
import com.blinkist.easylibrary.features.library.LibraryViewState.SortDialogClickedEvent
import com.blinkist.easylibrary.ktx.launchCatching
import com.blinkist.easylibrary.livedata.SafeMediatorLiveData
import com.tfcporciuncula.flow.Preference
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class LibraryViewModel @Inject constructor(
  private val bookRepository: BookRepository,
  private val bookGrouper: BookGrouper,
  @SortByDescending private val sortByDescendingPreference: Preference<Boolean>,
  val adapter: LibraryAdapter
) : ViewModel() {

  private val state = SafeMediatorLiveData(initialValue = LibraryViewState())

  val sortByDescending get() = sortByDescendingPreference.get()

  init {
    bookRepository
      .books()
      .combine(sortByDescendingPreference.asFlow()) { books, sortByDescending ->
        state.update { copy(libraryItems = bookGrouper.groupBooksByWeek(books, sortByDescending)) }
      }
      .launchIn(viewModelScope)

    updateBooks()
  }

  fun state(): LiveData<LibraryViewState> = state

  fun updateBooks() = viewModelScope.launchCatching(
    block = {
      state.update { copy(isLoading = true) }
      bookRepository.updateBooks()
      state.update { copy(isLoading = false) }
    },
    onFailure = ::handleFailure
  )

  private fun handleFailure(throwable: Throwable) {
    if (throwable !is IOException) Timber.e(throwable)
    state.update {
      copy(
        isLoading = false,
        errorEvent = if (throwable is IOException) ErrorEvent.Network() else ErrorEvent.Unexpected()
      )
    }
  }

  fun onArrangeByDescendingClicked() = onArrangeBooksClicked(sortByDescending = true)

  fun onArrangeByAscendingClicked() = onArrangeBooksClicked(sortByDescending = false)

  private fun onArrangeBooksClicked(sortByDescending: Boolean) {
    sortByDescendingPreference.set(sortByDescending)
    state.update { copy(sortDialogClickedEvent = SortDialogClickedEvent()) }
  }
}
