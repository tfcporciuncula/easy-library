package com.blinkist.easylibrary.model

import java.text.SimpleDateFormat
import java.util.*

fun newBookRaw(
    id: Long = 2020,
    publishedDate: String = "2018-03-03",
    title: String = "title",
    authors: String = "authors",
    thumbnail: String = "thumbnail",
    url: String = "url"
) = BookRaw(id, publishedDate, title, authors, thumbnail, url)

fun newBook(
    id: Long = 2020,
    publishedDate: String = "2018-03-03",
    publishedDateTime: Long = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(publishedDate).time,
    title: String = "title",
    authors: String = "authors",
    thumbnail: String = "thumbnail",
    url: String = "url"
) = Book(id, publishedDate, publishedDateTime, title, authors, thumbnail, url)

fun newWeekSection(
    initialDate: String = "init",
    finalDate: String = "final"
) = WeekSection(initialDate, finalDate)
