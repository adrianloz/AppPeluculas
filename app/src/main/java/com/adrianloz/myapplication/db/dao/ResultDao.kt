package com.adrianloz.myapplication.db.dao

import androidx.room.*
import com.adrianloz.myapplication.models.Result
import com.adrianloz.myapplication.models.Results

@Dao
interface ResultDao {

    @Insert
    suspend fun insert(result: Result) : Long
    @Delete
    suspend fun delete(result: Result): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMany(results: List<Result>): List<Long>

    @Query("select * from results where type=:type")
    suspend fun getResultsByType(type: String): List<Result>
}