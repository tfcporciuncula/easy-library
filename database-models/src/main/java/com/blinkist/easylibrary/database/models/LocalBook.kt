package com.blinkist.easylibrary.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.LocalDate

@Entity(tableName = "books")
data class LocalBook(
  @PrimaryKey
  val id: Long,

  @ColumnInfo(name = "published_date")
  val publishedDate: LocalDate,

  @ColumnInfo(name = "title")
  val title: String,

  @ColumnInfo(name = "authors")
  val authors: String,

  @ColumnInfo(name = "thumbnail")
  val thumbnail: String,

  @ColumnInfo(name = "url")
  val url: String
)
