package com.blinkist.easylibrary.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.blinkist.easylibrary.model.LocalBook

@Dao
interface BookDao {

  @Query("SELECT * FROM books")
  fun books(): LiveData<List<LocalBook>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(localBooks: List<LocalBook>)

  @Query("DELETE FROM books")
  suspend fun clear()

  @Transaction suspend fun clearAndInsert(localBooks: List<LocalBook>) {
    clear()
    insert(localBooks)
  }
}
