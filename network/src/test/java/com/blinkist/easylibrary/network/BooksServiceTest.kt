package com.blinkist.easylibrary.network

import com.blinkist.easylibrary.network.model.RemoteBook
import com.blinkist.easylibrary.network.test.RestMockRule
import com.google.common.truth.Truth.assertThat
import io.appflate.restmock.RESTMockServer
import io.appflate.restmock.utils.RequestMatchers.pathContains
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.threeten.bp.LocalDate

class BooksServiceTest {

  @get:Rule var restMockRule = RestMockRule()

  private val books =
    """
    [
      {
        "id": 1,
        "title": "book1",
        "authors": "author1",
        "publishedDate": "2018-04-03",
        "thumbnail": "thumbnail1",
        "url": "url1"
      },
      {
        "id": 2,
        "title": "book2",
        "authors": "author2",
        "publishedDate": "2018-04-04",
        "thumbnail": "thumbnail2",
        "url": "url2"
      }
    ]
    """

  @Test fun `test books`() {
    RESTMockServer.whenGET(pathContains("books")).thenReturnString(200, books)

    runBlocking {
      assertThat(restMockRule.booksService.books()).isEqualTo(
        listOf(
          RemoteBook(
            id = 1,
            title = "book1",
            authors = "author1",
            publishedDate = LocalDate.parse("2018-04-03"),
            thumbnail = "thumbnail1",
            url = "url1"
          ),
          RemoteBook(
            id = 2,
            title = "book2",
            authors = "author2",
            publishedDate = LocalDate.parse("2018-04-04"),
            thumbnail = "thumbnail2",
            url = "url2"
          )
        )
      )
    }
  }
}
