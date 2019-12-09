package com.blinkist.easylibrary.features.library

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blinkist.easylibrary.data.BookDao
import com.blinkist.easylibrary.features.library.LibraryViewState.ErrorEvent
import com.blinkist.easylibrary.features.library.LibraryViewState.SortDialogClickedEvent
import com.blinkist.easylibrary.ktx.launchCatching
import com.blinkist.easylibrary.livedata.SafeMediatorLiveData
import com.blinkist.easylibrary.model.BookMapper
import com.blinkist.easylibrary.service.BooksService
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class LibraryViewModel @Inject constructor(
  private val booksService: BooksService,
  private val bookMapper: BookMapper,
  private val bookDao: BookDao,
  private val bookGrouper: BookGrouper,
  private val sortByDescendingPreference: SortByDescendingPreference,
  val adapter: LibraryAdapter
) : ViewModel() {

  private val state = SafeMediatorLiveData(initialValue = LibraryViewState())

  private val books = bookDao.books()

  var sortByDescending
    get() = sortByDescendingPreference.get()
    private set(value) {
      sortByDescendingPreference.set(value)
    }

  init {
    state.addSource(books) {
      state.update { copy(books = bookGrouper.groupBooksByWeek(it, sortByDescending)) }
    }

    updateBooks()
  }

  fun state(): LiveData<LibraryViewState> = state

  fun updateBooks() = viewModelScope.launchCatching(
    block = {
      state.update { copy(isLoading = true) }
      bookDao.clearAndInsert(bookMapper.fromRaw(booksService.books()))
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

  fun onArrangeByDescendingClicked() = rearrangeBooks(sortByDescending = true)

  fun onArrangeByAscendingClicked() = rearrangeBooks(sortByDescending = false)

  private fun rearrangeBooks(sortByDescending: Boolean) {
    state.update { copy(sortDialogClickedEvent = SortDialogClickedEvent()) }
    if (this.sortByDescending == sortByDescending) return

    books.value?.let {
      this.sortByDescending = sortByDescending
      state.update { copy(books = bookGrouper.groupBooksByWeek(it, sortByDescending)) }
    }
  }
}
