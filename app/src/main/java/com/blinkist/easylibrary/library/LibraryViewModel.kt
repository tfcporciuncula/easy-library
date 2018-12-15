package com.blinkist.easylibrary.library

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.blinkist.easylibrary.data.BookDao
import com.blinkist.easylibrary.livedata.SafeMediatorLiveData
import com.blinkist.easylibrary.model.BookMapper
import com.blinkist.easylibrary.service.BooksService
import io.reactivex.disposables.Disposables
import timber.log.Timber
import javax.inject.Inject

class LibraryViewModel @Inject constructor(
    private val booksService: BooksService,
    private val bookMapper: BookMapper,
    private val bookDao: BookDao,
    private val bookGrouper: BookGrouper,
    val adapter: LibraryAdapter
) : ViewModel() {

    private val state = SafeMediatorLiveData(LibraryViewState())

    private val books = bookDao.books()

    private var disposable = Disposables.empty()

    var sortByDescending = true
        private set

    init {
        state.addSource(books) { result ->
            result?.let {
                state.update(books = bookGrouper.groupBooksByWeek(it, sortByDescending))
            }
        }
    }

    override fun onCleared() = disposable.dispose()

    fun state(): LiveData<LibraryViewState> = state

    fun updateBooks() {
        booksService.books()
            .map(bookMapper::fromRaw)
            .doOnSubscribe {
                disposable = it
                state.update(isLoading = true)
            }
            .subscribe({
                bookDao.clear()
                bookDao.insert(it)
                state.postUpdate(isLoading = false)
            }, {
                Timber.e(it)
                state.postUpdate(isLoading = false, error = LibraryError())
            })
    }

    fun rearrangeBooks(sortByDescending: Boolean) {
        if (this.sortByDescending == sortByDescending) return

        books.value?.let {
            this.sortByDescending = sortByDescending
            state.update(books = bookGrouper.groupBooksByWeek(it, sortByDescending))
        }
    }
}
