package com.blinkist.easylibrary.library

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MediatorLiveData
import com.blinkist.easylibrary.EasyLibraryApplication
import com.blinkist.easylibrary.data.BookDao
import com.blinkist.easylibrary.service.LibraryService
import io.reactivex.Completable
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

    var sortByDescending = true
        private set

    val librariables = MediatorLiveData<List<Librariable>>()

    private val books by lazy { bookDao.books() }

    init {
        getApplication<EasyLibraryApplication>().component.inject(this)

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
