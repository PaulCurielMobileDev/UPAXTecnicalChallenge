package com.mexicandeveloper.upaxpruebatecnica.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


inline fun <reified T> Gson.fromJson(json: String) =
    fromJson<T>(json, object : TypeToken<T>() {}.type)

class ArrayListConverter {

    @TypeConverter
    fun fromStringArrayList(value: ArrayList<String>): String {

        return Gson().toJson(value)
    }

    @TypeConverter
    fun toStringArrayList(value: String): ArrayList<String> {
        return try {
            Gson().fromJson<ArrayList<String>>(value) //using extension function
        } catch (e: Exception) {
            arrayListOf()
        }
    }
}

@kotlin.jvm.Throws
fun String.getIdFromUrl():Int = try {
    val theUrls = this.dropLast(1).split("/")
    theUrls.last().toInt()
} catch (e : Exception){
    throw e
}


