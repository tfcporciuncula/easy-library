package com.blinkist.easylibrary.library

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.blinkist.easylibrary.data.BookDao
import com.blinkist.easylibrary.model.BookMapper
import com.blinkist.easylibrary.service.BooksService
import dagger.Lazy
import javax.inject.Inject

class LibraryViewModelFactory @Inject constructor(
    private val booksService: Lazy<BooksService>,
    private val bookMapper: Lazy<BookMapper>,
    private val bookDao: Lazy<BookDao>,
    private val bookGrouper: Lazy<BookGrouper>,
    private val adapter: Lazy<LibraryAdapter>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LibraryViewModel(
            booksService.get(),
            bookMapper.get(),
            bookDao.get(),
            bookGrouper.get(),
            adapter.get()
        ) as T
    }
}
