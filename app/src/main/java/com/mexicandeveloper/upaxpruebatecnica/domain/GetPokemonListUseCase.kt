package com.mexicandeveloper.upaxpruebatecnica.domain

import com.mexicandeveloper.upaxpruebatecnica.data.entities.PokemonEntity
import com.mexicandeveloper.upaxpruebatecnica.data.local.PokemonLocalRepository
import com.mexicandeveloper.upaxpruebatecnica.data.remote.PokemonRemoteRepository
import com.mexicandeveloper.upaxpruebatecnica.utils.State
import com.mexicandeveloper.upaxpruebatecnica.utils.getIdFromUrl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPokemonListUseCase @Inject constructor(
    private val remoteRepository: PokemonRemoteRepository,
    private val localRepository: PokemonLocalRepository
) {

    suspend operator fun invoke(offset: Int): Flow<State<List<PokemonEntity>>> = flow {
        emit(State.LoadingState)
        localRepository.loadRange(offset).collect { pokemonList ->
            if (pokemonList.isEmpty()) {
                remoteRepository.getPokemonList(offset).collect {
                    when (it) {
                        is State.ErrorState -> emit(State.ErrorState(it.exception))
                        is State.LoadingState -> emit(State.LoadingState)
                        is State.DataState -> {
                            it.data.let { data ->
                                    val ans = mutableListOf<PokemonEntity>()
                                    for (pokemon in data) {
                                        ans.add(
                                            PokemonEntity(
                                                pokemon.url.getIdFromUrl(),
                                                pokemon.name,
                                                pokemon.url
                                            )
                                        )
                                    }
                                    localRepository.insertAll(ans)
                                    emit(State.DataState(ans))
                            }
                        }
                    }
                }
            } else {
                emit(State.DataState(pokemonList))
            }
        }
    }
}
