package com.blinkist.easylibrary.features.library

import com.blinkist.easylibrary.livedata.EmptyLiveDataEvent

data class LibraryViewState(
  val books: List<LibraryItem> = emptyList(),
  val isLoading: Boolean = false,
  val errorEvent: ErrorEvent? = null,
  val sortDialogClickedEvent: SortDialogClickedEvent? = null
) {

  sealed class ErrorEvent : EmptyLiveDataEvent() {
    class Network : ErrorEvent()
    class Unexpected : ErrorEvent()
  }

  class SortDialogClickedEvent : EmptyLiveDataEvent()
}
