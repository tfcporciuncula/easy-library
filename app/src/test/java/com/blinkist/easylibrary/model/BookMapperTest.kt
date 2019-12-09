package com.blinkist.easylibrary.model

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.text.DateFormat
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class BookMapperTest {

  @Mock private lateinit var dateFormat: DateFormat

  @InjectMocks private lateinit var bookMapper: BookMapper

  @Test fun testMapping() {
    val bookRaw = RemoteBook(
      id = 10,
      publishedDate = "10/10/2010",
      title = "title",
      authors = "authors",
      thumbnail = "thumbnail",
      url = "url"
    )

    val time = 101010L
    given(dateFormat.parse(bookRaw.publishedDate)).willReturn(Date(time))

    val book = bookMapper.fromRaw(listOf(bookRaw)).first()

    assertThat(book.id).isEqualTo(bookRaw.id)
    assertThat(book.publishedDate).isEqualTo(bookRaw.publishedDate)
    assertThat(book.publishedDateTime).isEqualTo(time)
    assertThat(book.title).isEqualTo(bookRaw.title)
    assertThat(book.authors).isEqualTo(bookRaw.authors)
    assertThat(book.thumbnail).isEqualTo(bookRaw.thumbnail)
    assertThat(book.url).isEqualTo(bookRaw.url)
  }
}
