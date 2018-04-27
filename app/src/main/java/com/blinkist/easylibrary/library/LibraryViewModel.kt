package com.blinkist.easylibrary.library

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import com.blinkist.easylibrary.EasyLibraryApplication
import com.blinkist.easylibrary.data.BookDao
import com.blinkist.easylibrary.model.Book
import com.blinkist.easylibrary.model.WeekSection
import com.blinkist.easylibrary.model.belongsTo
import com.blinkist.easylibrary.service.LibraryService
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class LibraryViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var libraryService: LibraryService

    @Inject
    lateinit var booksDao: BookDao

    @Inject
    lateinit var adapter: LibraryAdapter

    private var sortByDescending = true

    init {
        getApplication<EasyLibraryApplication>().component.inject(this)
    }

    fun books(): LiveData<List<Librariable>> = Transformations.map(booksDao.booksLive()) {
        groupBooksByWeek(it)
    }

    fun updateBooks(): Completable = libraryService.books()
        .doOnSuccess {
            booksDao.clear()
            booksDao.insert(it)
        }
        .toCompletable()

    fun booksSortedDifferently(): Single<List<Librariable>> {
        sortByDescending = !sortByDescending
        return booksDao.books().map(::groupBooksByWeek)
    }

    private fun groupBooksByWeek(books: List<Book>): List<Librariable> {
        if (books.isEmpty()) return emptyList()

        val sortedBooks = if (sortByDescending) {
            books.sortedByDescending { it.publishedDateTime }
        } else {
            books.sortedBy { it.publishedDateTime }
        }
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
