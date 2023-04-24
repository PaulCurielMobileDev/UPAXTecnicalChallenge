package com.mexicandeveloper.upaxpruebatecnica.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mexicandeveloper.upaxpruebatecnica.data.models.Sprites


class Utils{

}
fun getSprites(sprites: Sprites?):ArrayList<String>{
    val mutableList = arrayListOf<String>()
    sprites?.let {
        if (!it.backDefault.isNullOrEmpty()) mutableList.add(it.backDefault!!)
        if (!it.backFemale.isNullOrEmpty()) mutableList.add(it.backFemale!!)
        if (!it.backShiny.isNullOrEmpty()) mutableList.add(it.backShiny!!)
        if (!it.backShinyFemale.isNullOrEmpty()) mutableList.add(it.backShinyFemale!!)
        if (!it.frontDefault.isNullOrEmpty()) mutableList.add(it.frontDefault!!)
        if (!it.frontFemale.isNullOrEmpty()) mutableList.add(it.frontFemale!!)
        if (!it.frontShiny.isNullOrEmpty()) mutableList.add(it.frontShiny!!)
        if (!it.frontShinyFemale.isNullOrEmpty()) mutableList.add(it.frontShinyFemale!!)
    }
    return mutableList
}
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


