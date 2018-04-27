package com.blinkist.easylibrary.library

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import com.blinkist.easylibrary.EasyLibraryApplication
import com.blinkist.easylibrary.data.BookDao
import com.blinkist.easylibrary.service.LibraryService
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class LibraryViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var libraryService: LibraryService

    @Inject
    lateinit var bookDao: BookDao

    @Inject
    lateinit var bookGrouper: BookGrouper

    @Inject
    lateinit var adapter: LibraryAdapter

    private var sortByDescending = true

    init {
        getApplication<EasyLibraryApplication>().component.inject(this)
    }

    fun books(): LiveData<List<Librariable>> = Transformations.map(bookDao.booksLive()) {
        bookGrouper.groupBooksByWeek(it)
    }

    fun updateBooks(): Completable = libraryService.books()
        .doOnSuccess {
            bookDao.clear()
            bookDao.insert(it)
        }
        .toCompletable()

    fun booksSortedDifferently(): Single<List<Librariable>> {
        sortByDescending = !sortByDescending
        return bookDao.books().map { bookGrouper.groupBooksByWeek(it, sortByDescending) }
    }
}
