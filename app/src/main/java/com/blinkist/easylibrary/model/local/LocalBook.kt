package com.blinkist.easylibrary.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class LocalBook(
  @PrimaryKey
  val id: Long,

  @ColumnInfo(name = "published_date")
  val publishedDate: String,

  @ColumnInfo(name = "published_date_time")
  val publishedDateTime: Long,

  @ColumnInfo(name = "title")
  val title: String,

  @ColumnInfo(name = "authors")
  val authors: String,

  @ColumnInfo(name = "thumbnail")
  val thumbnail: String,

  @ColumnInfo(name = "url")
  val url: String
)
