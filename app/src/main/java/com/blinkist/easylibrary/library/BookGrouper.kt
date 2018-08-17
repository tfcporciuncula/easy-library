package com.blinkist.easylibrary.library

import com.blinkist.easylibrary.model.Book
import com.blinkist.easylibrary.model.WeekSection
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class BookGrouper @Inject constructor() {

    private val calendar = Calendar.getInstance().apply {
        firstDayOfWeek = Calendar.MONDAY
    }

    private val dateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.LONG)

    fun groupBooksByWeek(books: List<Book>, sortByDescending: Boolean): List<Librariable> {
        if (books.isEmpty()) return emptyList()

        val sortedBooks = sortBooks(sortByDescending, books)
        val groupedBooks = mutableListOf<Librariable>()

        var currentDate = sortedBooks.first().publishedDateTime
        var currentWeekSection = buildWeekSection(currentDate)
        groupedBooks.add(currentWeekSection)

        sortedBooks.forEach {
            if (it.belongsToSameWeekAs(currentDate)) {
                groupedBooks.add(it)
            } else {
                currentDate = it.publishedDateTime
                currentWeekSection = buildWeekSection(currentDate)
                groupedBooks.add(currentWeekSection)
                groupedBooks.add(it)
            }
        }

        return groupedBooks
    }

    private fun sortBooks(sortByDescending: Boolean, books: List<Book>): List<Book> {
        return if (sortByDescending) {
            books.sortedByDescending { it.publishedDateTime }
        } else {
            books.sortedBy { it.publishedDateTime }
        }
    }

    private fun buildWeekSection(date: Long): WeekSection {
        calendar.time = Date(date)

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        val initialDate = calendar.time

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        val finalDate = calendar.time

        return WeekSection(dateFormat.format(initialDate), dateFormat.format(finalDate))
    }

    private fun Book.belongsToSameWeekAs(date: Long): Boolean {
        calendar.time = Date(publishedDateTime)
        val bookYear = calendar.get(Calendar.YEAR)
        val bookWeekOfYear = calendar.get(Calendar.WEEK_OF_YEAR)

        calendar.time = Date(date)
        val sectionYear = calendar.get(Calendar.YEAR)
        val sectionWeekOfYear = calendar.get(Calendar.WEEK_OF_YEAR)

        return bookYear == sectionYear && bookWeekOfYear == sectionWeekOfYear
    }
}
