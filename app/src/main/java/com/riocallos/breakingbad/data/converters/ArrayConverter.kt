package com.riocallos.breakingbad.data.converters

import androidx.room.TypeConverter
import com.google.gson.Gson


class ArrayConverter {

    @TypeConverter
    fun stringArrayToJson(value: Array<String>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToStringArray(value: String) = Gson().fromJson(value, Array<String>::class.java)

    @TypeConverter
    fun intArrayToJson(value: Array<Int>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToIntArray(value: String) = Gson().fromJson(value, Array<Int>::class.java)

}