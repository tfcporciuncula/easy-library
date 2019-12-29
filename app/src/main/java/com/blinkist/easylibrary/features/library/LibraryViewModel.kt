package com.blinkist.easylibrary.features.library

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blinkist.easylibrary.di.SharedPreferencesModule.LibrarySortOrderPreference
import com.blinkist.easylibrary.features.library.LibraryViewState.ErrorEvent
import com.blinkist.easylibrary.features.library.LibraryViewState.SortDialogClickedEvent
import com.blinkist.easylibrary.ktx.launchCatching
import com.blinkist.easylibrary.livedata.NonNullMutableLiveData
import com.tfcporciuncula.flow.Preference
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class LibraryViewModel @Inject constructor(
  private val bookRepository: BookRepository,
  private val bookGrouper: BookGrouper,
  @LibrarySortOrderPreference private val sortOrderPreference: Preference<LibrarySortOrder>,
  val adapter: LibraryAdapter
) : ViewModel() {

  private val state = NonNullMutableLiveData(initialValue = LibraryViewState())

  val currentSortOrder get() = sortOrderPreference.get()

  init {
    bookRepository
      .books()
      .combine(sortOrderPreference.asFlow()) { books, sortOrder ->
        state.update { copy(libraryItems = bookGrouper.groupBooksByWeek(books, sortOrder)) }
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

  fun onArrangeByAscendingClicked() = onArrangeBooksClicked(LibrarySortOrder.ASCENDING)

  fun onArrangeByDescendingClicked() = onArrangeBooksClicked(LibrarySortOrder.DESCENDING)

  private fun onArrangeBooksClicked(sortOrder: LibrarySortOrder) {
    sortOrderPreference.set(sortOrder)
    state.update { copy(sortDialogClickedEvent = SortDialogClickedEvent()) }
  }
}
