package com.blinkist.easylibrary.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteBook(
  val id: Long?,
  val publishedDate: String?,
  val title: String?,
  val authors: String?,
  val thumbnail: String?,
  val url: String?
)
