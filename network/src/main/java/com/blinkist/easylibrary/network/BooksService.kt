package com.blinkist.easylibrary.network

import com.blinkist.easylibrary.network.models.RemoteBook
import retrofit2.http.GET

interface BooksService {

  @GET("books")
  suspend fun books(): List<RemoteBook>
}
