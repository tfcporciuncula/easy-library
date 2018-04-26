package com.blinkist.easylibrary.model

import android.annotation.SuppressLint
import com.blinkist.easylibrary.library.Librariable
import java.text.SimpleDateFormat
import java.util.*

data class WeekSection(
    private val book: Book,
    val year: Int = book.year,
    val weekOfYear: Int = book.weekOfYear,
    val title: String = buildTitle(book.publishedDateTime)
) : Librariable

@SuppressLint("SimpleDateFormat")
private fun buildTitle(bookPublishedDate: Long): String {
    val calendar = Calendar.getInstance().apply {
        firstDayOfWeek = Calendar.MONDAY
        time = Date(bookPublishedDate)
    }

    calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
    val initialDate = SimpleDateFormat("yyyy-MM-dd").format(calendar.time)

    calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
    val finalDate = SimpleDateFormat("yyyy-MM-dd").format(calendar.time)

    return "$initialDate to $finalDate"
}
