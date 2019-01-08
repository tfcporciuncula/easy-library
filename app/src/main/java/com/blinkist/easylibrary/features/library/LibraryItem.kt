package com.blinkist.easylibrary.features.library

sealed class LibraryItem {

  abstract class Book : LibraryItem()

  abstract class Section : LibraryItem()
}
