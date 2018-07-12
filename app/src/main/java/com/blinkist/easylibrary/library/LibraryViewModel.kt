package com.blinkist.easylibrary.library

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.ViewModel
import com.blinkist.easylibrary.data.BookDao
import com.blinkist.easylibrary.service.LibraryService
import io.reactivex.Completable

class LibraryViewModel(
    private val libraryService: LibraryService,
    private val bookDao: BookDao,
    private val bookGrouper: BookGrouper,
    val adapter: LibraryAdapter
) : ViewModel() {

    var sortByDescending = true
        private set

    val librariables = MediatorLiveData<List<Librariable>>()

    private val books = bookDao.books()

    init {
        librariables.addSource(books) {
            it?.let { librariables.value = bookGrouper.groupBooksByWeek(it, sortByDescending) }
        }
    }

    fun updateBooks(): Completable = libraryService.books()
        .doOnSuccess {
            bookDao.clear()
            bookDao.insert(it)
        }
        .toCompletable()

    fun rearrangeBooks(sortByDescending: Boolean) = books.value?.let {
        this.sortByDescending = sortByDescending
        librariables.value = bookGrouper.groupBooksByWeek(it, sortByDescending)
    }
}
