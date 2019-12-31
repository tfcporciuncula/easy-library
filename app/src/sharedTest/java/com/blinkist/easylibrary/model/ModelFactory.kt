package com.blinkist.easylibrary.model

import com.blinkist.easylibrary.model.local.LocalBook
import com.blinkist.easylibrary.model.presentation.Book
import com.blinkist.easylibrary.model.presentation.WeekSection
import com.blinkist.easylibrary.model.remote.RemoteBook
import org.threeten.bp.LocalDate

fun newRemoteBook(
  id: Long = 1010,
  publishedDate: String = "2018-03-03",
  title: String = "title!",
  authors: String = "authors!",
  thumbnail: String = "thumbnail!",
  url: String = "url"
) = RemoteBook(id, LocalDate.parse(publishedDate), title, authors, thumbnail, url)

fun newLocalBook(
  id: Long = 2020,
  publishedDate: String = "2019-03-03",
  title: String = "title.",
  authors: String = "authors.",
  thumbnail: String = "thumbnail.",
  url: String = "url"
) = LocalBook(id, LocalDate.parse(publishedDate), title, authors, thumbnail, url)

fun newBook(
  id: Long = 3030,
  publishedDate: String = "2020-03-03",
  title: String = "title;",
  authors: String = "authors;",
  imageUrl: String = "thumbnail;",
  url: String = "url"
) = Book(id, LocalDate.parse(publishedDate), publishedDate, title, authors, imageUrl, url)

fun newWeekSection(
  initialDate: String = "init",
  finalDate: String = "final"
) = WeekSection(initialDate, finalDate)
