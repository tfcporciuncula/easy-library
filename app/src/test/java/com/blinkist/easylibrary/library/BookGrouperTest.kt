package com.blinkist.easylibrary.library

import com.blinkist.easylibrary.model.Book
import com.blinkist.easylibrary.model.WeekSection
import com.blinkist.easylibrary.model.newBook
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class BookGrouperTest {

    private val bookGrouper get() = BookGrouper()

    @Test fun testBooksFromTheSameWeek() {
        val books = listOf(
            newBook(publishedDate = "2018-04-28"),
            newBook(publishedDate = "2018-04-27")
        )

        val groupedBooks = bookGrouper.groupBooksByWeek(books, sortByDescending = true)

        assertThat(groupedBooks[0]).isInstanceOf(WeekSection::class.java)
        assertThat(groupedBooks[1]).isEqualTo(books[0])
        assertThat(groupedBooks[2]).isEqualTo(books[1])
    }

    @Test fun testBooksFromDifferentWeek() {
        val books = listOf(
            newBook(publishedDate = "2018-04-28"),
            newBook(publishedDate = "2017-04-27")
        )

        val groupedBooks = bookGrouper.groupBooksByWeek(books, sortByDescending = true)

        assertThat(groupedBooks[0]).isInstanceOf(WeekSection::class.java)
        assertThat(groupedBooks[1]).isEqualTo(books[0])
        assertThat(groupedBooks[2]).isInstanceOf(WeekSection::class.java)
        assertThat(groupedBooks[3]).isEqualTo(books[1])
    }

    @Test fun testChangingBooksSortOrder() {
        val books = listOf(
            newBook(publishedDate = "2018-04-03"),
            newBook(publishedDate = "2018-04-04")
        )

        val descendingBooks = bookGrouper.groupBooksByWeek(books, sortByDescending = true)
        val ascendingBooks = bookGrouper.groupBooksByWeek(books, sortByDescending = false)

        assertThat(ascendingBooks.filter { it is Book }).isEqualTo(books)
        assertThat(descendingBooks.filter { it is Book }).isEqualTo(books.reversed())
    }
}
