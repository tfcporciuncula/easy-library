package com.blinkist.easylibrary.library

import com.blinkist.easylibrary.livedata.EmptyLiveDataEvent
import com.blinkist.easylibrary.livedata.SafeMediatorLiveData

data class LibraryViewState(
    val books: List<Librariable> = emptyList(),
    val isLoading: Boolean = false,
    val error: LibraryError? = null
)

class LibraryError : EmptyLiveDataEvent()

fun SafeMediatorLiveData<LibraryViewState>.update(
    books: List<Librariable> = value.books,
    isLoading: Boolean = value.isLoading,
    error: LibraryError? = value.error
) {
    value = value.copy(books = books, isLoading = isLoading, error = error)
}

fun SafeMediatorLiveData<LibraryViewState>.postUpdate(
    books: List<Librariable> = value.books,
    isLoading: Boolean = value.isLoading,
    error: LibraryError? = value.error
) {
    postValue(value.copy(books = books, isLoading = isLoading, error = error))
}
