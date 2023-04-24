package com.mexicandeveloper.upaxpruebatecnica.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PokemonDetailEntity (
    @PrimaryKey  var id: Int,
    @ColumnInfo("baseExperience") var baseExperience: Int,
    @ColumnInfo("height") var height: Int ,
    @ColumnInfo("name") var name: String,
    @ColumnInfo("order") var order: Int,
    @ColumnInfo("sprites") var sprites: ArrayList<String>,
    @ColumnInfo("type1") var type1:String,
    @ColumnInfo("type2") var type2:String,
    @ColumnInfo("weight") var weight: Int
)