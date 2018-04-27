package com.blinkist.easylibrary.model

object ModelFactory {

    fun newBook(
        id: Long = 2020,
        publishedDate: String = "2018-03-03",
        title: String = "title",
        authors: String = "authors",
        thumbnail: String = "thumbnail",
        url: String = "url"
    ) = Book(
        id = id,
        publishedDate = publishedDate,
        title = title,
        authors = authors,
        thumbnail = thumbnail,
        url = url
    )
}
