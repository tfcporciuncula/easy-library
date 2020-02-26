package com.blinkist.easylibrary.model.repositories

import com.blinkist.easylibrary.database.BookDao
import com.blinkist.easylibrary.model.mappers.BookMapper
import com.blinkist.easylibrary.network.BooksService
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BookRepository @Inject constructor(
  private val booksService: BooksService,
  private val bookDao: BookDao,
  private val bookMapper: BookMapper
) {

  suspend fun currentBooks() = bookDao.currentBooks()

  fun books() = bookDao.books().map { bookMapper.localToPresentation(it) }

  suspend fun updateBooks() = bookDao.clearAndInsert(bookMapper.remoteToLocal(booksService.books()))
}
