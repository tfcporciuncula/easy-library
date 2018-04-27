package com.blinkist.easylibrary.library

import com.blinkist.easylibrary.model.Book
import com.blinkist.easylibrary.model.WeekSection
import com.blinkist.easylibrary.model.belongsTo

class BookGrouper {

    fun groupBooksByWeek(books: List<Book>, sortByDescending: Boolean = true): List<Librariable> {
        if (books.isEmpty()) return emptyList()

        val sortedBooks = if (sortByDescending) {
            books.sortedByDescending { it.publishedDateTime }
        } else {
            books.sortedBy { it.publishedDateTime }
        }
        val listItems = mutableListOf<Librariable>()

        var currentWeekSection = WeekSection(sortedBooks.first())
        listItems.add(currentWeekSection)

        sortedBooks.forEach {
            if (it.belongsTo(currentWeekSection)) {
                listItems.add(it)
            } else {
                currentWeekSection = WeekSection(it)
                listItems.add(currentWeekSection)
                listItems.add(it)
            }
        }

        return listItems
    }
}
