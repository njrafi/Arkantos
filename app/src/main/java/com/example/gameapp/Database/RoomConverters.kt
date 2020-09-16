package com.example.gameapp.Database

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

class RoomConverters {
    @TypeConverter
    fun fromStringListToString(value: List<String>): String {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, String::class.java)
        val jsonAdapter = moshi.adapter<List<String>>(type)
        return jsonAdapter.toJson(value)
    }

    @TypeConverter
    fun fromStringToListString(value: String): List<String>? {
        val moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, String::class.java)
        val jsonAdapter = moshi.adapter<List<String>>(type)
        return jsonAdapter.fromJson(value)
    }
}