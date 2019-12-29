package com.blinkist.easylibrary.model

import com.blinkist.easylibrary.model.local.LocalBook
import com.blinkist.easylibrary.model.presentation.WeekSection
import com.blinkist.easylibrary.model.remote.RemoteBook
import org.threeten.bp.LocalDate

fun newBookRaw(
  id: Long = 2020,
  publishedDate: String = "2018-03-03",
  title: String = "title",
  authors: String = "authors",
  thumbnail: String = "thumbnail",
  url: String = "url"
) = RemoteBook(
  id,
  LocalDate.parse(publishedDate),
  title,
  authors,
  thumbnail,
  url
)

fun newBook(
  id: Long = 2020,
  publishedDate: String = "2018-03-03",
  title: String = "title",
  authors: String = "authors",
  thumbnail: String = "thumbnail",
  url: String = "url"
) = LocalBook(
  id,
  LocalDate.parse(publishedDate),
  title,
  authors,
  thumbnail,
  url
)

fun newWeekSection(
  initialDate: String = "init",
  finalDate: String = "final"
) = WeekSection(initialDate, finalDate)
