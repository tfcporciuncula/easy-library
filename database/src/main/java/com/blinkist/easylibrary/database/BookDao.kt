package com.blinkist.easylibrary.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.blinkist.easylibrary.database.model.LocalBook
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {

  @Query("SELECT * FROM books")
  fun books(): Flow<List<LocalBook>>

  @Query("SELECT COUNT(*) FROM books")
  suspend fun count(): Int

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(localBooks: List<LocalBook>)

  @Query("DELETE FROM books")
  suspend fun clear()

  @Transaction suspend fun clearAndInsert(localBooks: List<LocalBook>) {
    clear()
    insert(localBooks)
  }
}
