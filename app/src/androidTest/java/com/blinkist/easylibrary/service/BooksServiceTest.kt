package com.blinkist.easylibrary.service

import android.support.test.runner.AndroidJUnit4
import com.blinkist.easylibrary.di.RetrofitTestModule
import com.blinkist.easylibrary.model.Book
import com.google.common.truth.Truth.assertThat
import io.appflate.restmock.RESTMockServer
import io.appflate.restmock.utils.RequestMatchers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.text.SimpleDateFormat

@RunWith(AndroidJUnit4::class)
class BooksServiceTest {

    private val books = """
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

    private val booksService get() = RetrofitTestModule().buildLibraryService()

    @Before fun setup() {
        RESTMockServer.reset()
        RESTMockServer.whenGET(RequestMatchers.pathContains("books"))
            .thenReturnString(200, books)
    }

    @Test fun testMapping() {
        booksService.books().test()
            .assertValues(
                listOf(
                    Book(
                        id = 1,
                        title = "book1",
                        authors = "author1",
                        publishedDate = "2018-04-03",
                        thumbnail = "thumbnail1",
                        url = "url1"
                    ),
                    Book(
                        id = 2,
                        title = "book2",
                        authors = "author2",
                        publishedDate = "2018-04-04",
                        thumbnail = "thumbnail2",
                        url = "url2"
                    )
                )
            )
    }

    @Test fun testPublishedDateTime() {
        val (book1, book2) = booksService.books().test().values().first()

        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        assertThat(book1.publishedDateTime).isEqualTo(dateFormat.parse(book1.publishedDate).time)
        assertThat(book2.publishedDateTime).isEqualTo(dateFormat.parse(book2.publishedDate).time)
    }
}
