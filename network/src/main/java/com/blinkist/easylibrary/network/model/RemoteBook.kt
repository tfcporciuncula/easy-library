package com.blinkist.easylibrary.network.model

import com.squareup.moshi.JsonClass
import org.threeten.bp.LocalDate

@JsonClass(generateAdapter = true)
data class RemoteBook(
  val id: Long,
  val publishedDate: LocalDate,
  val title: String,
  val authors: String,
  val thumbnail: String,
  val url: String
)
