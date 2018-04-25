package com.blinkist.easylibrary.service

import com.blinkist.easylibrary.model.Book
import io.reactivex.Single
import retrofit2.http.GET

interface BooksService {

    @GET("books")
    fun books(): Single<List<Book>>
}
