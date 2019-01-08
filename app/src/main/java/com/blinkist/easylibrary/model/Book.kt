package com.blinkist.easylibrary.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.blinkist.easylibrary.di.ServiceModule
import com.blinkist.easylibrary.features.library.LibraryItem
import com.squareup.moshi.JsonClass
import dagger.Reusable
import java.text.DateFormat
import javax.inject.Inject

@Entity(tableName = "books")
data class Book(
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
) : LibraryItem.Book()

@JsonClass(generateAdapter = true)
data class BookRaw(
  val id: Long?,
  val publishedDate: String?,
  val title: String?,
  val authors: String?,
  val thumbnail: String?,
  val url: String?
)

@Reusable
class BookMapper @Inject constructor(@ServiceModule.ServiceDateFormat private val dateFormat: DateFormat) {

  fun fromRaw(booksRaw: List<BookRaw>): List<Book> = booksRaw.map(::fromRaw)

  private fun fromRaw(bookRaw: BookRaw) = Book(
    id = bookRaw.id ?: throw IllegalArgumentException("Book has null id"),

    publishedDate = bookRaw.publishedDate ?: throw IllegalArgumentException("Book has null publishedDate"),

    publishedDateTime = dateFormat.parse(bookRaw.publishedDate).time,

    title = bookRaw.title ?: throw IllegalArgumentException("Book has null title"),

    authors = bookRaw.authors ?: throw IllegalArgumentException("Book has null authors"),

    thumbnail = bookRaw.thumbnail ?: throw IllegalArgumentException("Book has null thumbnail"),

    url = bookRaw.url ?: throw IllegalArgumentException("Book has null url")
  )
}
