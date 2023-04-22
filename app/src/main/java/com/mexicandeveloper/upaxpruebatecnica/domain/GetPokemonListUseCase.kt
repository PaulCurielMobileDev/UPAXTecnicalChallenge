package com.mexicandeveloper.upaxpruebatecnica.domain

import com.mexicandeveloper.upaxpruebatecnica.data.remote.PokemonRemoteRepository
import javax.inject.Inject

class GetPokemonListUseCase @Inject constructor(private val remoteRepository: PokemonRemoteRepository) {

    suspend operator fun invoke(offset: Int) = remoteRepository.getPokemonList(offset)
}