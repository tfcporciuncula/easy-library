package com.blinkist.easylibrary.model.presentation

import com.blinkist.easylibrary.features.library.LibraryItem

data class Book(
  val id: Long,
  val publishedDate: String,
  val publishedDateTime: Long,
  val title: String,
  val authors: String,
  val thumbnail: String,
  val url: String
) : LibraryItem.Book()
