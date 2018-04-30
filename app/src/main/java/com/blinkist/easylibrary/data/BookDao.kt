package com.blinkist.easylibrary.data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.blinkist.easylibrary.model.Book
import io.reactivex.Single

@Dao
interface BookDao {

    @Query("SELECT * FROM books")
    fun booksLive(): LiveData<List<Book>>

    @Query("SELECT * FROM books")
    fun books(): Single<List<Book>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(books: List<Book>)

    @Query("DELETE FROM books")
    fun clear()
}
