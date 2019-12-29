package com.blinkist.easylibrary.features.library

enum class LibrarySortOrder {
  ASCENDING, DESCENDING;

  companion object {
    val DEFAULT = LibrarySortOrder.DESCENDING
  }
}
