package com.mexicandeveloper.upaxpruebatecnica.data.local

import com.mexicandeveloper.upaxpruebatecnica.data.entities.PokemonEntity
import com.mexicandeveloper.upaxpruebatecnica.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokemonLocalRepositoryImpl @Inject constructor(private val dao: PokemonDAO) :
    PokemonLocalRepository {
    override suspend fun loadRange(offSet: Int): Flow<List<PokemonEntity>> {
        return flow {
            emit(dao.loadRange(offSet, Constants.POKEMON_LIMIT_PAGE))
        }
    }

    override suspend fun insertAll(pokemons: List<PokemonEntity>) {
        dao.insertAll(*pokemons.toTypedArray())
    }
}