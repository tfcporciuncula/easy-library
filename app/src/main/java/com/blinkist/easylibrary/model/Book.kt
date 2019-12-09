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
data class RemoteBook(
  val id: Long?,
  val publishedDate: String?,
  val title: String?,
  val authors: String?,
  val thumbnail: String?,
  val url: String?
)

@Reusable
class BookMapper @Inject constructor(@ServiceModule.ServiceDateFormat private val dateFormat: DateFormat) {

  fun fromRaw(remoteBooks: List<RemoteBook>): List<Book> = remoteBooks.map(::fromRaw)

  private fun fromRaw(remoteBook: RemoteBook) = Book(
    id = remoteBook.id ?: throw IllegalArgumentException("Book has null id"),

    publishedDate = remoteBook.publishedDate ?: throw IllegalArgumentException("Book has null publishedDate"),

    publishedDateTime = dateFormat.parse(remoteBook.publishedDate)?.time ?: throw IllegalArgumentException("Book has invalid publishedDate"),

    title = remoteBook.title ?: throw IllegalArgumentException("Book has null title"),

    authors = remoteBook.authors ?: throw IllegalArgumentException("Book has null authors"),

    thumbnail = remoteBook.thumbnail ?: throw IllegalArgumentException("Book has null thumbnail"),

    url = remoteBook.url ?: throw IllegalArgumentException("Book has null url")
  )
}
