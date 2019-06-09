package com.blinkist.easylibrary.service

import com.blinkist.easylibrary.model.BookRaw
import retrofit2.http.GET

interface BooksService {

  @GET("books")
  suspend fun books(): List<BookRaw>
}
