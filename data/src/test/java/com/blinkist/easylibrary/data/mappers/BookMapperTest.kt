package com.blinkist.easylibrary.data.mappers

import com.blinkist.easylibrary.database.models.LocalBook
import com.blinkist.easylibrary.network.models.RemoteBook
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.threeten.bp.LocalDate

class BookMapperTest {

  private lateinit var bookMapper: BookMapper

  @Before fun setup() {
    bookMapper = BookMapper()
  }

  @Test fun `test remote to local mapping`() {
    val remoteBook = RemoteBook(
      id = 10,
      publishedDate = LocalDate.parse("2010-10-10"),
      title = "title",
      authors = "authors",
      thumbnail = "thumbnail",
      url = "url"
    )

    val localBook = bookMapper.remoteToLocal(listOf(remoteBook)).first()

    assertThat(localBook.id).isEqualTo(remoteBook.id)
    assertThat(localBook.publishedDate).isEqualTo(remoteBook.publishedDate)
    assertThat(localBook.title).isEqualTo(remoteBook.title)
    assertThat(localBook.authors).isEqualTo(remoteBook.authors)
    assertThat(localBook.thumbnail).isEqualTo(remoteBook.thumbnail)
    assertThat(localBook.url).isEqualTo(remoteBook.url)
  }

  @Test fun `test local to presentation mapping`() {
    val localBook = LocalBook(
      id = 10,
      publishedDate = LocalDate.parse("2010-10-10"),
      title = "title",
      authors = "authors",
      thumbnail = "thumbnail",
      url = "url"
    )

    val book = bookMapper.localToPresentation(listOf(localBook)).first()

    assertThat(book.id).isEqualTo(localBook.id)
    assertThat(book.publishedDate).isEqualTo(localBook.publishedDate)
    assertThat(book.title).isEqualTo(localBook.title)
    assertThat(book.authors).isEqualTo(localBook.authors)
    assertThat(book.imageUrl).isEqualTo(localBook.thumbnail)
    assertThat(book.url).isEqualTo(localBook.url)
  }
}
