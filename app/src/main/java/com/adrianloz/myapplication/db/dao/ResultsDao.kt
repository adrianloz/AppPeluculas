package com.adrianloz.myapplication.db.dao

import androidx.room.*
import com.adrianloz.myapplication.models.Results

@Dao
interface ResultsDao {
    @Insert
    suspend fun insert(result: Results) : Long
    @Delete
    suspend fun delete(result: Results): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertManyVideo(results : List<Results>) : List<Long>

    @Query("select * from result where typeVideo=:typeVideo")
    suspend fun getResultsByidVideo(typeVideo: String): List<Results>
}