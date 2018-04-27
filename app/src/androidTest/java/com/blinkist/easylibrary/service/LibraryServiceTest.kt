package com.blinkist.easylibrary.service

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.blinkist.easylibrary.model.Book
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import io.appflate.restmock.RESTMockServer
import io.appflate.restmock.RESTMockServerStarter
import io.appflate.restmock.android.AndroidAssetsFileParser
import io.appflate.restmock.android.AndroidLogger
import io.appflate.restmock.utils.RequestMatchers
import okhttp3.OkHttpClient
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.text.SimpleDateFormat

@RunWith(AndroidJUnit4::class)
class LibraryServiceTest {

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

    private val libraryService
        get() = Retrofit.Builder()
            .baseUrl(RESTMockServer.getUrl())
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().add(KotlinJsonAdapterFactory()).build()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(LibraryService::class.java)

    @Before
    fun setup() {
        RESTMockServerStarter.startSync(AndroidAssetsFileParser(InstrumentationRegistry.getContext()), AndroidLogger())
        RESTMockServer.whenGET(RequestMatchers.pathContains("books"))
            .thenReturnString(200, books)
    }

    @Test
    fun testMapping() {
        libraryService.books().test()
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

    @Test
    fun testPublishedDateTime() {
        val (book1, book2) = libraryService.books().test().values().first()

        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        assertEquals(book1.publishedDateTime, dateFormat.parse(book1.publishedDate).time)
        assertEquals(book2.publishedDateTime, dateFormat.parse(book2.publishedDate).time)
    }
}
