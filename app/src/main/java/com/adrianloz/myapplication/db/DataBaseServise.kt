package com.adrianloz.myapplication.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.adrianloz.myapplication.db.dao.ResultDao
import com.adrianloz.myapplication.db.dao.ResultsDao
import com.adrianloz.myapplication.models.Result
import com.adrianloz.myapplication.models.Results


@Database(entities = [Results::class, Result::class], exportSchema = false, version = 1)
@TypeConverters(IdsToStringConverter::class)
abstract class DataBaseServise : RoomDatabase() {
    abstract fun resultDao(): ResultDao
    abstract fun resultsDao(): ResultsDao
}