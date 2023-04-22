package com.mexicandeveloper.upaxpruebatecnica.data.remote

import com.mexicandeveloper.upaxpruebatecnica.data.entities.Pokemon
import com.mexicandeveloper.upaxpruebatecnica.data.entities.PokemonResponse
import com.mexicandeveloper.upaxpruebatecnica.utils.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonRemoteRepositoryImpl @Inject constructor(private val api: PokemonAPI) :PokemonRemoteRepository{

     override suspend fun getPokemonList(offset: Int): Flow<State<List<Pokemon>>> {
        return flow {
            emit(State.LoadingState)
            try {
                val response = api.getPokemonList(offset, 25)
                emit(State.DataState(response.results))
            } catch (e: Exception) {
                emit(State.ErrorState(e))
            }
        }
    }

    override suspend fun getPokemon(pokemonNumber: Int): Flow<State<PokemonResponse>> = flow {

        emit(State.LoadingState)
        try {
            emit(State.DataState(api.getPokemon(pokemonNumber)))
        }catch (e:java.lang.Exception){
            emit(State.ErrorState(e))
        }
    }
}