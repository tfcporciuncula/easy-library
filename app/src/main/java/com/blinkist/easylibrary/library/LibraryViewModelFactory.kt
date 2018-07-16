package com.blinkist.easylibrary.library

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.blinkist.easylibrary.data.BookDao
import com.blinkist.easylibrary.service.LibraryService
import dagger.Lazy

class LibraryViewModelFactory(
    private val libraryService: Lazy<LibraryService>,
    private val bookDao: Lazy<BookDao>,
    private val bookGrouper: Lazy<BookGrouper>,
    private val adapter: Lazy<LibraryAdapter>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LibraryViewModel(libraryService.get(), bookDao.get(), bookGrouper.get(), adapter.get()) as T
    }
}
