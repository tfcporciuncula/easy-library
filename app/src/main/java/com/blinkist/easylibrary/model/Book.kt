package com.blinkist.easylibrary.model

data class Book(
    val id: Long,
    val title: String,
    val authors: String,
    val publishedDate: String,
    val thumbnail: String,
    val url: String
)
