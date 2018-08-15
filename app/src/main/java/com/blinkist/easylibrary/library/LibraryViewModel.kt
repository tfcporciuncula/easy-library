package com.blinkist.easylibrary.library

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.ViewModel
import com.blinkist.easylibrary.data.BookDao
import com.blinkist.easylibrary.model.BookMapper
import com.blinkist.easylibrary.service.BooksService
import io.reactivex.Completable
import javax.inject.Inject

class LibraryViewModel @Inject constructor(
    private val booksService: BooksService,
    private val bookMapper: BookMapper,
    private val bookDao: BookDao,
    private val bookGrouper: BookGrouper,
    val adapter: LibraryAdapter
) : ViewModel() {

    private val books = bookDao.books()
    private val librariables = MediatorLiveData<List<Librariable>>()

    var sortByDescending = true
        private set

    init {
        librariables.addSource(books) {
            it?.let { librariables.value = bookGrouper.groupBooksByWeek(it, sortByDescending) }
        }
    }

    fun books(): LiveData<List<Librariable>> = librariables

    fun updateBooks(): Completable = booksService.books()
        .map(bookMapper::fromRaw)
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
