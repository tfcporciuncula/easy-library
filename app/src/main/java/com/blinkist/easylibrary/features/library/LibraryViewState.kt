package com.blinkist.easylibrary.features.library

import com.blinkist.easylibrary.livedata.EmptyLiveDataEvent
import com.blinkist.easylibrary.livedata.SafeMediatorLiveData

data class LibraryViewState(
  val books: List<LibraryItem> = emptyList(),
  val isLoading: Boolean = false,
  val errorEvent: ErrorEvent? = null,
  val sortDialogClickedEvent: SortDialogClickedEvent? = null
)

sealed class ErrorEvent : EmptyLiveDataEvent() {
  class Network : ErrorEvent()
  class Unexpected : ErrorEvent()
}

class SortDialogClickedEvent : EmptyLiveDataEvent()

fun SafeMediatorLiveData<LibraryViewState>.update(
  books: List<LibraryItem> = value.books,
  isLoading: Boolean = value.isLoading,
  errorEvent: ErrorEvent? = value.errorEvent,
  sortDialogClickedEvent: SortDialogClickedEvent? = value.sortDialogClickedEvent
) {
  value = value.copy(books, isLoading, errorEvent, sortDialogClickedEvent)
}
