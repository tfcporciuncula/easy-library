package com.blinkist.easylibrary.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.blinkist.easylibrary.model.Book

@Dao
interface BookDao {

  @Query("SELECT * FROM books")
  fun books(): LiveData<List<Book>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(books: List<Book>)

  @Query("DELETE FROM books")
  fun clear()
}
