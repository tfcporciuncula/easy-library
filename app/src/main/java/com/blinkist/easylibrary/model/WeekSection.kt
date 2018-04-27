package com.blinkist.easylibrary.model

import com.blinkist.easylibrary.library.Librariable
import java.text.SimpleDateFormat
import java.util.*

data class WeekSection(
    private val book: Book,
    val year: Int = book.year,
    val weekOfYear: Int = book.weekOfYear,
    val title: String = buildTitle(book.publishedDateTime)
) : Librariable

val dateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.LONG)

private fun buildTitle(bookPublishedDate: Long): String {
    val calendar = Calendar.getInstance().apply {
        firstDayOfWeek = Calendar.MONDAY
        time = Date(bookPublishedDate)
    }

    calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
    val initialDate = dateFormat.format(calendar.time)

    calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
    val finalDate = dateFormat.format(calendar.time)

    return "$initialDate to $finalDate"
}
