package com.blinkist.easylibrary.library

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.blinkist.easylibrary.data.BookDao
import com.blinkist.easylibrary.service.LibraryService

class LibraryViewModelFactory(
    private val libraryService: LibraryService,
    private val bookDao: BookDao,
    private val bookGrouper: BookGrouper,
    private val adapter: LibraryAdapter
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LibraryViewModel(libraryService, bookDao, bookGrouper, adapter) as T
    }
}
