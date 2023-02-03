package com.ariefrahmanfajri.pilem.util

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
abstract class ListConverters<T> {
    @TypeConverter
    open fun listToString(list: List<T>): String {
        val gson = Gson()
        val type = object : TypeToken<List<T>>(){}.type
        return gson.toJson(list, type)
    }

    @TypeConverter
    open fun stringToList(string: String): List<T> {
        val gson = Gson()
        val type = object : TypeToken<List<T>>(){}.type
        return gson.fromJson(string, type)
    }
}