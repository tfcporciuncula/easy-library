package com.blinkist.easylibrary.model

object ModelFactory {

    fun newBook(
        id: Long = 2020,
        publishedDate: String = "2018-03-03",
        publishedDateTime: Long = 101010,
        title: String = "title",
        authors: String = "authors",
        thumbnail: String = "thumbnail",
        url: String = "url"
    ): Book {
        return Book(id, publishedDate, publishedDateTime, title, authors, thumbnail, url)
    }
}
