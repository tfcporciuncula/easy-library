package com.blinkist.easylibrary.features.library

import com.blinkist.easylibrary.database.BookDao
import com.blinkist.easylibrary.model.mappers.BookMapper
import com.blinkist.easylibrary.service.BooksService
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BookRepository @Inject constructor(
  private val booksService: BooksService,
  private val bookDao: BookDao,
  private val bookMapper: BookMapper
) {

  fun books() = bookDao.books().map { bookMapper.localToPresentation(it) }

  suspend fun updateBooks() = bookDao.clearAndInsert(bookMapper.remoteToLocal(booksService.books()))
}
