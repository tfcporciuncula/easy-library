package com.blinkist.easylibrary.service

import com.blinkist.easylibrary.model.BookRaw
import io.appflate.restmock.JVMFileParser
import io.appflate.restmock.RESTMockServer
import io.appflate.restmock.RESTMockServerStarter
import io.appflate.restmock.logging.NOOpLogger
import io.appflate.restmock.utils.RequestMatchers.pathContains
import okhttp3.OkHttpClient
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

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

    private val booksService
        get() = Retrofit.Builder()
            .baseUrl(RESTMockServer.getUrl())
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(BooksService::class.java)

    @Before fun setup() {
        RESTMockServerStarter.startSync(JVMFileParser(), NOOpLogger())
        RESTMockServer.whenGET(pathContains("books")).thenReturnString(200, books)
    }

    @After fun tearDown() = RESTMockServer.shutdown()

    @Test fun testMapping() {
        booksService.books().test()
            .assertResult(
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
