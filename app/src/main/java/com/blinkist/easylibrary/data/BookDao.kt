package com.blinkist.easylibrary.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.blinkist.easylibrary.model.Book

@Dao
interface BookDao {

  @Query("SELECT * FROM books")
  fun books(): LiveData<List<Book>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(books: List<Book>)

  @Query("DELETE FROM books")
  suspend fun clear()

  @Transaction suspend fun clearAndInsert(books: List<Book>) {
    clear()
    insert(books)
  }
}
