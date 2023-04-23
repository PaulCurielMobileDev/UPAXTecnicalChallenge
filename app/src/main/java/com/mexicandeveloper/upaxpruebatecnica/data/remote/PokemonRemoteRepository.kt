package com.mexicandeveloper.upaxpruebatecnica.data.remote

import com.mexicandeveloper.upaxpruebatecnica.data.models.Pokemon
import com.mexicandeveloper.upaxpruebatecnica.data.models.PokemonResponse
import com.mexicandeveloper.upaxpruebatecnica.utils.State
import kotlinx.coroutines.flow.Flow


interface PokemonRemoteRepository {

    suspend fun getPokemonList(offset: Int): Flow<State<List<Pokemon>>>
    suspend fun getPokemon(pokemonNumber: Int): Flow<State<PokemonResponse>>
}