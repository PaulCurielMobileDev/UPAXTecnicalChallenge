package com.mexicandeveloper.upaxpruebatecnica.domain

import com.mexicandeveloper.upaxpruebatecnica.data.PokemonAPI
import com.mexicandeveloper.upaxpruebatecnica.data.entities.Pokemon
import javax.inject.Inject

class GetPokemonListUseCase @Inject constructor(private val api: PokemonAPI) {

    suspend operator fun invoke(offset: Int): List<Pokemon> {
        val response = api.getPokemonList(offset, 25)

        return response.results
    }
}