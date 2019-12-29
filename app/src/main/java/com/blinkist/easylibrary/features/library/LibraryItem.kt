package com.blinkist.easylibrary.features.library

sealed class LibraryItem {

  abstract class Book : LibraryItem()

  abstract class Section : LibraryItem()

  // these are here to avoid equality check warnings in the adapter
  abstract override fun equals(other: Any?): Boolean
  abstract override fun hashCode(): Int
}
