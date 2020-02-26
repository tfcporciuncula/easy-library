package com.blinkist.easylibrary.features.library

import androidx.annotation.StringRes
import com.blinkist.easylibrary.util.livedata.LiveDataEvent

data class LibraryViewState(
  val libraryItems: List<LibraryItem> = emptyList(),
  val isLoading: Boolean = false,
  val isThemePopupOpen: Boolean = false,
  val currentSortOrder: LibrarySortOrder = LibrarySortOrder.DEFAULT,
  val snackbarEvent: SnackbarEvent? = null,
  val navigationEvent: NavigationEvent? = null
) {

  class SnackbarEvent(@StringRes val messageResId: Int) : LiveDataEvent()

  sealed class NavigationEvent : LiveDataEvent() {
    class ToWebView(val url: String) : NavigationEvent()
    class ToSortOrderDialog : NavigationEvent()
  }
}
