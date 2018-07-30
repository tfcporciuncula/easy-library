package com.blinkist.easylibrary.service

import android.support.test.runner.AndroidJUnit4
import com.blinkist.easylibrary.base.BaseInstrumentationTest
import com.blinkist.easylibrary.model.BookRaw
import io.appflate.restmock.RESTMockServer
import io.appflate.restmock.utils.RequestMatchers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class BooksServiceTest : BaseInstrumentationTest() {

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

    @Inject lateinit var booksService: BooksService

    @Before override fun setup() {
        super.setup()
        component.inject(this)

        RESTMockServer.whenGET(RequestMatchers.pathContains("books"))
            .thenReturnString(200, books)
    }

    @Test fun testMapping() {
        booksService.books().test()
            .assertValues(
                listOf(
                    BookRaw(
                        id = 1,
                        title = "book1",
                        authors = "author1",
                        publishedDate = "2018-04-03",
                        thumbnail = "thumbnail1",
                        url = "url1"
                    ),
                    BookRaw(
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
}
