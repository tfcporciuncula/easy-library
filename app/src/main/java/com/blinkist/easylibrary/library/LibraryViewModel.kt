package com.blinkist.easylibrary.library

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.blinkist.easylibrary.EasyLibraryApplication
import com.blinkist.easylibrary.model.Book
import com.blinkist.easylibrary.service.BooksService
import io.reactivex.Single
import javax.inject.Inject

class LibraryViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var booksService: BooksService

    init {
        getApplication<EasyLibraryApplication>().component.inject(this)
    }

    fun getBooks(): Single<List<Book>> {
        return booksService.books()
    }
}
