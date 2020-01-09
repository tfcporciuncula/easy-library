package com.blinkist.easylibrary.features.library

import com.blinkist.easylibrary.model.presentation.Book
import com.blinkist.easylibrary.model.presentation.WeekSection
import org.threeten.bp.ZoneId
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

// TODO: rewrite logic with three ten
private val Book.dateTime get() = publishedDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

class BookGrouper @Inject constructor() {

  private val calendar = Calendar.getInstance().apply {
    firstDayOfWeek = Calendar.MONDAY
  }

  private val dateFormat = SimpleDateFormat.getDateInstance(SimpleDateFormat.LONG)

  fun groupBooksByWeek(books: List<Book>, sortOrder: LibrarySortOrder): List<LibraryItem> {
    if (books.isEmpty()) return emptyList()

    val sortedBooks = sortBooks(sortOrder, books)
    val groupedBooks = mutableListOf<LibraryItem>()

    var currentDate = sortedBooks.first().dateTime
    var currentWeekSection = buildWeekSection(currentDate)
    groupedBooks.add(currentWeekSection)

    sortedBooks.forEach {
      if (it.belongsToSameWeekAs(currentDate)) {
        groupedBooks.add(it)
      } else {
        currentDate = it.dateTime
        currentWeekSection = buildWeekSection(currentDate)
        groupedBooks.add(currentWeekSection)
        groupedBooks.add(it)
      }
    }

    return groupedBooks
  }

  private fun sortBooks(sortOrder: LibrarySortOrder, books: List<Book>) = when (sortOrder) {
    LibrarySortOrder.ASCENDING -> books.sortedBy { it.dateTime }
    LibrarySortOrder.DESCENDING -> books.sortedByDescending { it.dateTime }
  }

  private fun buildWeekSection(date: Long): WeekSection {
    calendar.time = Date(date)

    calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
    val initialDate = calendar.time

    calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
    val finalDate = calendar.time

    return WeekSection(
      dateFormat.format(initialDate),
      dateFormat.format(finalDate)
    )
  }

  private fun Book.belongsToSameWeekAs(date: Long): Boolean {
    calendar.time = Date(dateTime)
    val bookYear = calendar.get(Calendar.YEAR)
    val bookWeekOfYear = calendar.get(Calendar.WEEK_OF_YEAR)

    calendar.time = Date(date)
    val sectionYear = calendar.get(Calendar.YEAR)
    val sectionWeekOfYear = calendar.get(Calendar.WEEK_OF_YEAR)

    return bookYear == sectionYear && bookWeekOfYear == sectionWeekOfYear
  }
}
