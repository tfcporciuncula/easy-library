package com.blinkist.easylibrary.library

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import com.blinkist.easylibrary.EasyLibraryApplication
import com.blinkist.easylibrary.data.BookDao
import com.blinkist.easylibrary.model.Book
import com.blinkist.easylibrary.model.WeekSection
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

    fun books(): LiveData<List<Librariable>> = Transformations.map(booksDao.books()) {
        groupBooksByWeek(it)
    }

    fun updateBooks(): Single<List<Book>> = libraryService.books()
        .doOnSuccess {
            Timber.d("Updating local storage with latest books from the server.")
            booksDao.clear()
            booksDao.insert(it)
        }

    private fun groupBooksByWeek(books: List<Book>): List<Librariable> {
        if (books.isEmpty()) return emptyList()

        val sortedBooks = books.sortedByDescending { it.publishedDateTime }
        val listItems = mutableListOf<Librariable>()

        var currentWeekSection = WeekSection(sortedBooks.first())
        listItems.add(currentWeekSection)

        sortedBooks.forEach {
            if (it.belongsTo(currentWeekSection)) {
                listItems.add(it)
            } else {
                currentWeekSection = WeekSection(it)
                listItems.add(currentWeekSection)
                listItems.add(it)
            }
        }

        return listItems
    }
}
