package com.blinkist.easylibrary.library

import com.blinkist.easylibrary.model.Book
import com.blinkist.easylibrary.model.ModelFactory.newBook
import com.blinkist.easylibrary.model.WeekSection
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class BookGrouperTest {

    private val calendar
        get() = Calendar.getInstance().apply {
            firstDayOfWeek = Calendar.MONDAY
        }

    private val dateFormat get() = SimpleDateFormat.getDateInstance(SimpleDateFormat.LONG)

    private val bookGrouper get() = BookGrouper(calendar, dateFormat)

    @Test
    fun testBooksFromTheSameWeek() {
        val books = listOf(
            newBook(publishedDate = "2018-04-28"),
            newBook(publishedDate = "2018-04-27")
        )

        val groupedBooks = bookGrouper.groupBooksByWeek(books)

        assertTrue(groupedBooks[0] is WeekSection)
        assertEquals(books[0], groupedBooks[1] as Book)
        assertEquals(books[1], groupedBooks[2] as Book)
    }

    @Test
    fun testBooksFromDifferentWeek() {
        val books = listOf(
            newBook(publishedDate = "2018-04-28"),
            newBook(publishedDate = "2017-04-27")
        )

        val groupedBooks = bookGrouper.groupBooksByWeek(books)

        assertTrue(groupedBooks[0] is WeekSection)
        assertEquals(books[0], groupedBooks[1] as Book)
        assertTrue(groupedBooks[2] is WeekSection)
        assertEquals(books[1], groupedBooks[3] as Book)
    }

    @Test
    fun testChangingBooksSortOrder() {
        val books = listOf(
            newBook(publishedDate = "2018-04-03"),
            newBook(publishedDate = "2018-04-04")
        )

        val descendingBooks = bookGrouper.groupBooksByWeek(books, sortByDescending = true)
        val ascendingBooks = bookGrouper.groupBooksByWeek(books, sortByDescending = false)

        assertEquals(books, ascendingBooks.filter { it is Book })
        assertEquals(books.reversed(), descendingBooks.filter { it is Book })
    }
}
