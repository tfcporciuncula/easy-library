package com.blinkist.easylibrary.library

sealed class LibraryItem {

    abstract class Book : LibraryItem()

    abstract class Section : LibraryItem()
}
