package com.blinkist.easylibrary.model

import java.text.SimpleDateFormat
import java.util.*

object ModelFactory {

    fun newBookRaw(
        id: Long = 2020,
        publishedDate: String = "2018-03-03",
        title: String = "title",
        authors: String = "authors",
        thumbnail: String = "thumbnail",
        url: String = "url"
    ) = BookRaw(
        id = id,
        publishedDate = publishedDate,
        title = title,
        authors = authors,
        thumbnail = thumbnail,
        url = url
    )

    fun newBook(
        id: Long = 2020,
        publishedDate: String = "2018-03-03",
        publishedDateTime: Long = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(publishedDate).time,
        title: String = "title",
        authors: String = "authors",
        thumbnail: String = "thumbnail",
        url: String = "url"
    ) = Book(
        id = id,
        publishedDate = publishedDate,
        publishedDateTime = publishedDateTime,
        title = title,
        authors = authors,
        thumbnail = thumbnail,
        url = url
    )

    fun newWeekSection(
        initialDate: String = "init",
        finalDate: String = "final"
    ) = WeekSection(initialDate, finalDate)
}
