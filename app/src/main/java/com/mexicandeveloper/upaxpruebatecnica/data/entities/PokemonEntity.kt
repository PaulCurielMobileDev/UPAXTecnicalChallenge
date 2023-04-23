package com.mexicandeveloper.upaxpruebatecnica.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PokemonEntity(
    @PrimaryKey var id:Int,
    @ColumnInfo(name = "name")var name:String,
    @ColumnInfo(name = "url")var url:String
)
