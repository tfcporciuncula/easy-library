package com.blinkist.easylibrary.data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.blinkist.easylibrary.model.Book
import io.reactivex.Flowable

@Dao
interface BookDao {

    @Query("SELECT * FROM books")
    fun books(): LiveData<List<Book>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg books: Book)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(books: List<Book>)

    @Delete
    fun delete(vararg books: Book)

    @Delete
    fun delete(books: List<Book>)

    @Query("DELETE FROM books")
    fun clear()
}
