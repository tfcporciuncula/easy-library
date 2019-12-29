package com.blinkist.easylibrary.features.library

import com.blinkist.easylibrary.livedata.EmptyLiveDataEvent

data class LibraryViewState(
  val libraryItems: List<LibraryItem> = emptyList(),
  val isLoading: Boolean = false,
  val currentSortOrder: LibrarySortOrder = LibrarySortOrder.DEFAULT,
  val errorEvent: ErrorEvent? = null,
  val sortDialogClickedEvent: SortDialogClickedEvent? = null
) {

  sealed class ErrorEvent : EmptyLiveDataEvent() {
    class Network : ErrorEvent()
    class Unexpected : ErrorEvent()
  }

  class SortDialogClickedEvent : EmptyLiveDataEvent()
}
