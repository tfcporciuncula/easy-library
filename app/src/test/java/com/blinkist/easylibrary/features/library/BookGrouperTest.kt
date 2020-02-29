package com.blinkist.easylibrary.features.library

import com.blinkist.easylibrary.model.newBook
import com.blinkist.easylibrary.models.presentation.Book
import com.blinkist.easylibrary.models.presentation.WeekSection
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class BookGrouperTest {

  private val bookGrouper get() = BookGrouper()

  @Test fun `test with books from the same week`() {
    val books = listOf(
      newBook(publishedDate = "2018-04-28"),
      newBook(publishedDate = "2018-04-27")
    )

    val groupedBooks = bookGrouper.groupBooksByWeek(books, LibrarySortOrder.DESCENDING)

    assertThat(groupedBooks[0] is WeekSection).isTrue()
    assertThat(groupedBooks[1]).isEqualTo(books[0])
    assertThat(groupedBooks[2]).isEqualTo(books[1])
  }

  @Test fun `test with books from different weeks`() {
    val books = listOf(
      newBook(publishedDate = "2018-04-28"),
      newBook(publishedDate = "2017-04-27")
    )

    val groupedBooks = bookGrouper.groupBooksByWeek(books, LibrarySortOrder.DESCENDING)

    assertThat(groupedBooks[0] is WeekSection).isTrue()
    assertThat(groupedBooks[1]).isEqualTo(books[0])
    assertThat(groupedBooks[2] is WeekSection).isTrue()
    assertThat(groupedBooks[3]).isEqualTo(books[1])
  }

  @Test fun `test with different sort orders`() {
    val books = listOf(
      newBook(publishedDate = "2018-04-03"),
      newBook(publishedDate = "2018-04-04")
    )

    val descendingBooks = bookGrouper.groupBooksByWeek(books, LibrarySortOrder.DESCENDING)
    val ascendingBooks = bookGrouper.groupBooksByWeek(books, LibrarySortOrder.ASCENDING)

    assertThat(ascendingBooks.filterIsInstance<Book>()).isEqualTo(books)
    assertThat(descendingBooks.filterIsInstance<Book>()).isEqualTo(books.reversed())
  }
}
