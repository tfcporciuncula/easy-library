package com.blinkist.easylibrary.database.model

import org.threeten.bp.LocalDate

fun newLocalBook(
  id: Long = 2020,
  publishedDate: String = "2019-03-03",
  title: String = "title.",
  authors: String = "authors.",
  thumbnail: String = "thumbnail.",
  url: String = "url"
) = LocalBook(id, LocalDate.parse(publishedDate), title, authors, thumbnail, url)
