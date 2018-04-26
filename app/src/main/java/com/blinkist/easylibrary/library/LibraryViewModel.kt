package com.blinkist.easylibrary.library

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.blinkist.easylibrary.EasyLibraryApplication
import com.blinkist.easylibrary.data.BookDao
import com.blinkist.easylibrary.model.Book
import com.blinkist.easylibrary.service.LibraryService
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject

class LibraryViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var libraryService: LibraryService

    @Inject
    lateinit var booksDao: BookDao

    init {
        getApplication<EasyLibraryApplication>().component.inject(this)
    }

    fun books(): LiveData<List<Book>> = booksDao.books()

    fun updateBooks(): Single<List<Book>> = libraryService.books()
        .doOnSuccess {
            Timber.d("Updating local storage with latest books from the server.")
            booksDao.clear()
            booksDao.insert(it)
        }
}
