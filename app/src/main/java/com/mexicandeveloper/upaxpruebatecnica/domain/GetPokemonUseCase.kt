package com.mexicandeveloper.upaxpruebatecnica.domain

import com.mexicandeveloper.upaxpruebatecnica.data.remote.PokemonRemoteRepository
import javax.inject.Inject

class GetPokemonUseCase @Inject constructor(private val remoteRepository: PokemonRemoteRepository) {

    suspend operator fun invoke(pokemonNumber: Int) = remoteRepository.getPokemon(pokemonNumber)
}