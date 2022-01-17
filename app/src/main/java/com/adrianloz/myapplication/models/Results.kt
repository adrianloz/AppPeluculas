package com.adrianloz.myapplication.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "result")
data class Results(
    @PrimaryKey
    var pKey: String = "" + System.currentTimeMillis(),
    var iso_639_1: String? = null,
    var iso_3166_1: String? = null,
    var name: String? = null,
    var key: String? = null,
    var site: String? = null,
    var size: Int = 0,
    var type: String? = null,
    var official: String? = null,
    var published_at: String? = null,
    var id: String? = null,
    var typeVideo : String? = null
) : Serializable