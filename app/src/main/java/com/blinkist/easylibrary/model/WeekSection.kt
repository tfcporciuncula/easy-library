package com.blinkist.easylibrary.model

import com.blinkist.easylibrary.library.Librariable
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

data class WeekSection(
    private val book: Book,
    val year: Int = calendar.apply { time = Date(book.publishedDateTime) }.get(Calendar.YEAR),
    val weekOfYear: Int = calendar.apply { time = Date(book.publishedDateTime) }.get(Calendar.WEEK_OF_YEAR),
    val title: String = buildTitle(book.publishedDateTime)
) : Librariable

private val calendar = Calendar.getInstance().apply {
    firstDayOfWeek = Calendar.MONDAY
}

private val dateFormat: DateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.LONG)

private fun buildTitle(bookPublishedDate: Long): String {
    calendar.time = Date(bookPublishedDate)

    calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
    val initialDate = dateFormat.format(calendar.time)

    calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
    val finalDate = dateFormat.format(calendar.time)

    return "$initialDate to $finalDate"
}

fun Book.belongsTo(weekSection: WeekSection): Boolean {
    calendar.time = Date(publishedDateTime)

    val bookYear = calendar.get(Calendar.YEAR)
    val bookWeekOfYear = calendar.get(Calendar.WEEK_OF_YEAR)

    return bookYear == weekSection.year && bookWeekOfYear == weekSection.weekOfYear
}
