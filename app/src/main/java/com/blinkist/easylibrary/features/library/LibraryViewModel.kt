package com.blinkist.easylibrary.features.library

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.blinkist.easylibrary.data.BookDao
import com.blinkist.easylibrary.livedata.SafeMediatorLiveData
import com.blinkist.easylibrary.model.BookMapper
import com.blinkist.easylibrary.service.BooksService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class LibraryViewModel @Inject constructor(
  private val booksService: BooksService,
  private val bookMapper: BookMapper,
  private val bookDao: BookDao,
  private val bookGrouper: BookGrouper,
  private val sortByDescendingPreference: SortByDescendingPreference,
  val adapter: LibraryAdapter
) : ViewModel() {

  private val state = SafeMediatorLiveData(initialValue = LibraryViewState())

  private val books = bookDao.books()

  private var disposables = CompositeDisposable()

  var sortByDescending
    get() = sortByDescendingPreference.get()
    private set(value) {
      sortByDescendingPreference.set(value)
    }

  init {
    state.addSource(books) { result ->
      result?.let { state.update(books = bookGrouper.groupBooksByWeek(it, sortByDescending)) }
    }
  }

  override fun onCleared() = disposables.dispose()

  fun state(): LiveData<LibraryViewState> = state

  fun updateBooks() = booksService.books()
    .map(bookMapper::fromRaw)
    .doOnSubscribe {
      state.update(isLoading = true)
    }
    .doOnSuccess {
      bookDao.clear()
      bookDao.insert(it)
    }
    .observeOn(AndroidSchedulers.mainThread())
    .subscribe({
      state.update(isLoading = false)
    }, {
      Timber.e(it)
      state.update(
        isLoading = false,
        error = if (it is IOException) LibraryError.Network() else LibraryError.Unexpected()
      )
    })
    .addTo(disposables)

  fun rearrangeBooks(sortByDescending: Boolean) {
    if (this.sortByDescending == sortByDescending) return

    books.value?.let {
      this.sortByDescending = sortByDescending
      state.update(books = bookGrouper.groupBooksByWeek(it, sortByDescending))
    }
  }
}
