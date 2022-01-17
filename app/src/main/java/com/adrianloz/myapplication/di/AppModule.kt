package com.adrianloz.myapplication.di

import android.content.Context
import androidx.room.Room
import com.adrianloz.myapplication.db.DataBaseServise
import com.adrianloz.myapplication.network.ApiService
import com.adrianloz.myapplication.utils.Helper
import com.adrianloz.myapplication.utils.Keys
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun getApiService() = ApiService.getInstance()

    @Provides
    fun localDb(@ApplicationContext context : Context) : DataBaseServise{
        return Room.databaseBuilder(context, DataBaseServise::class.java, Keys.DB_NAME)
            .build()
    }

    @Provides
    fun getHelper( @ApplicationContext context: Context):Helper=Helper(context)

}