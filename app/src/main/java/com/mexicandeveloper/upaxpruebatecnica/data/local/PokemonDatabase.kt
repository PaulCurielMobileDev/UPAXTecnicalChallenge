package com.mexicandeveloper.upaxpruebatecnica.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mexicandeveloper.upaxpruebatecnica.data.entities.PokemonDetailEntity
import com.mexicandeveloper.upaxpruebatecnica.data.entities.PokemonEntity
import com.mexicandeveloper.upaxpruebatecnica.utils.ArrayListConverter

@Database(entities = [PokemonEntity::class, PokemonDetailEntity::class], version = 1)
@TypeConverters(ArrayListConverter::class)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDAO
}