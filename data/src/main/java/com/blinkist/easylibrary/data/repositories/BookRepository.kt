package com.blinkist.easylibrary.data.repositories

import com.blinkist.easylibrary.data.mappers.BookMapper
import com.blinkist.easylibrary.database.BookDao
import com.blinkist.easylibrary.network.BooksService
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BookRepository @Inject constructor(
  private val booksService: BooksService,
  private val bookDao: BookDao,
  private val bookMapper: BookMapper
) {

  fun books() = bookDao.books().map { bookMapper.localToPresentation(it) }

  suspend fun hasNoBooks() = bookDao.count() == 0

  suspend fun updateBooks() = bookDao.clearAndInsert(bookMapper.remoteToLocal(booksService.books()))
}
