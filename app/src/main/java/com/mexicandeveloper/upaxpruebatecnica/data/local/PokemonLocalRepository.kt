package com.mexicandeveloper.upaxpruebatecnica.data.local

import com.mexicandeveloper.upaxpruebatecnica.data.entities.PokemonDetailEntity
import com.mexicandeveloper.upaxpruebatecnica.data.entities.PokemonEntity
import kotlinx.coroutines.flow.Flow

interface PokemonLocalRepository {

    suspend fun loadRange(offSet:Int):Flow<List<PokemonEntity>>

    suspend fun insertAll(pokemons:List<PokemonEntity>)

    suspend fun getPokemonDetail(pokemonId:Int):Flow<List<PokemonDetailEntity>>

    suspend fun insertPokemonDetail(pokemon:PokemonDetailEntity)
}