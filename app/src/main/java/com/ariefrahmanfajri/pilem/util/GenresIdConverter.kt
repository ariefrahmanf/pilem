package com.ariefrahmanfajri.pilem.util

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/*
@ProvidedTypeConverter
class GenresIdConverter: ListConverters<Int>() {

    @TypeConverter
    override fun listToString(list: List<Int>): String {
        return listToString(list)
    }

    @TypeConverter
    override fun stringToList(string: String): List<Int> {
        return super.stringToList(string)
    }
}*/

@ProvidedTypeConverter
class GenresIdConverter {

    @TypeConverter
    fun listToString(list: List<Int>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun stringToList(value: String): List<Int> {
        val type = object : TypeToken<List<Int>>(){}.type
        return Gson().fromJson(value, type)
    }
}
