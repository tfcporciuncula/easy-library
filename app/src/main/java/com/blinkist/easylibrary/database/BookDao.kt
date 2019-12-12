package com.blinkist.easylibrary.database

import androidx.room.*
import com.blinkist.easylibrary.model.local.LocalBook
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

  @Query("SELECT * FROM books")
  fun books(): Flow<List<LocalBook>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(localBooks: List<LocalBook>)

  @Query("DELETE FROM books")
  suspend fun clear()

  @Transaction suspend fun clearAndInsert(localBooks: List<LocalBook>) {
    clear()
    insert(localBooks)
  }
}
