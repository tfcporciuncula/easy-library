package com.blinkist.easylibrary.features.library

import com.blinkist.easylibrary.livedata.EmptyEvent
import com.blinkist.easylibrary.livedata.EmptyLiveDataEvent
import com.blinkist.easylibrary.livedata.Event
import com.blinkist.easylibrary.livedata.SafeMediatorLiveData

data class LibraryViewState(
  val books: List<LibraryItem> = emptyList(),
  val isLoading: Boolean = false,
  val error: LibraryError? = null
)

interface LibraryError: EmptyEvent {
  class Network : LibraryError, EmptyLiveDataEvent()
  class Unexpected : LibraryError, EmptyLiveDataEvent()
}

fun SafeMediatorLiveData<LibraryViewState>.update(
  books: List<LibraryItem> = value.books,
  isLoading: Boolean = value.isLoading,
  error: LibraryError? = value.error
) {
  value = value.copy(books = books, isLoading = isLoading, error = error)
}
