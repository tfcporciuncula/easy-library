package com.blinkist.easylibrary.model.presentation

import org.threeten.bp.LocalDate

sealed class LibraryItem

data class Book(
  val id: Long,
  val publishedDate: LocalDate,
  val publishedDateText: String,
  val title: String,
  val authors: String,
  val imageUrl: String,
  val url: String
) : LibraryItem()

data class WeekSection(
  val initialDate: String,
  val finalDate: String
) : LibraryItem()
