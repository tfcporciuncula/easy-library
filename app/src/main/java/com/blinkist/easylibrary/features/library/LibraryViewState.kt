package com.blinkist.easylibrary.features.library

import androidx.annotation.StringRes
import com.blinkist.easylibrary.livedata.EmptyLiveDataEvent
import com.blinkist.easylibrary.livedata.LiveDataEvent

data class LibraryViewState(
  val libraryItems: List<LibraryItem> = emptyList(),
  val isLoading: Boolean = false,
  val currentSortOrder: LibrarySortOrder = LibrarySortOrder.DEFAULT,
  val snackbarEvent: SnackbarEvent? = null,
  val sortDialogClickedEvent: SortDialogClickedEvent? = null
) {

  class SnackbarEvent(@StringRes messageResId: Int) : LiveDataEvent<Int>(messageResId)

  class SortDialogClickedEvent : EmptyLiveDataEvent()
}
