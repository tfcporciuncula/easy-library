package com.blinkist.easylibrary.model.presentation

import com.blinkist.easylibrary.features.library.LibraryItem
import org.threeten.bp.LocalDate

data class Book(
  val id: Long,
  val publishedDate: LocalDate,
  val publishedDateText: String,
  val title: String,
  val authors: String,
  val imageUrl: String,
  val url: String
) : LibraryItem.Book()
