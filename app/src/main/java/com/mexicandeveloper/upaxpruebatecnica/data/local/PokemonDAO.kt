package com.mexicandeveloper.upaxpruebatecnica.data.local

import androidx.room.*
import com.mexicandeveloper.upaxpruebatecnica.data.entities.PokemonDetailEntity
import com.mexicandeveloper.upaxpruebatecnica.data.entities.PokemonEntity

@Dao
interface PokemonDAO {
    @Query("SELECT * FROM PokemonEntity")
    fun getAll(): List<PokemonEntity>

    @Query("SELECT * FROM PokemonEntity WHERE id IN (:pokeIds)")
    fun loadAllByIds(pokeIds: IntArray): List<PokemonEntity>

    @Query("SELECT * FROM PokemonEntity WHERE id > :offset LIMIT :limit")
    fun loadRange(offset: Int, limit: Int): List<PokemonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg pokemon: PokemonEntity)

    @Delete
    fun delete(pokemon: PokemonEntity)

    @Delete
    fun delete(pokemon: PokemonDetailEntity)

    @Query("SELECT * FROM PokemonDetailEntity WHERE id = :id")
    fun getPokemonDetail(id: Int): List<PokemonDetailEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDetail(vararg pokemon: PokemonDetailEntity)

}