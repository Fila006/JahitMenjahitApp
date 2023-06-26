package com.example.jahitmenjahit.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.jahitmenjahit.model.Sew
import kotlinx.coroutines.flow.Flow


@Dao
interface SewDao {
    @Query("SELECT * FROM sew_table ORDER BY name ASC")
    fun getAllSew() : Flow<List<Sew>>

    @Insert
    suspend fun insertSew(sew: Sew)

    @Delete
    suspend fun deleteSew(sew: Sew)

    @Update
    suspend fun updateSew(sew: Sew)
}