package com.mexicandeveloper.upaxpruebatecnica.domain

import com.mexicandeveloper.upaxpruebatecnica.data.entities.PokemonDetailEntity
import com.mexicandeveloper.upaxpruebatecnica.data.local.PokemonLocalRepository
import com.mexicandeveloper.upaxpruebatecnica.data.remote.PokemonRemoteRepository
import com.mexicandeveloper.upaxpruebatecnica.utils.State
import com.mexicandeveloper.upaxpruebatecnica.utils.getSprites
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPokemonUseCase @Inject constructor(
    private val remoteRepository: PokemonRemoteRepository,
    private val localRepository: PokemonLocalRepository
) {

    suspend operator fun invoke(pokemonNumber: Int): Flow<State<PokemonDetailEntity>> {
        return flow {
            emit(State.LoadingState)

            localRepository.getPokemonDetail(pokemonNumber).collect {
                if (it.isEmpty()) {
                    remoteRepository.getPokemon(pokemonNumber).collect {
                        when (it) {
                            is State.DataState -> {

                                it.data.let { pokemon ->

                                    val type1:String = pokemon.types[0].type?.name ?: "--"
                                    val type2:String =
                                        if (pokemon.types.size > 1) pokemon.types[1].type?.name
                                            ?: "--" else "--"
                                    val theDetail = PokemonDetailEntity(
                                        pokemon.id,
                                        pokemon.baseExperience ?: 0,
                                        pokemon.height ?: 0,
                                        pokemon.name ?: "",
                                        pokemon.order ?: 0,
                                        getSprites(pokemon.sprites),
                                        type1, type2,
                                        pokemon.weight?: 0
                                    )
                                    localRepository.insertPokemonDetail(theDetail)
                                    emit(State.DataState(theDetail))

                                }
                            }
                            is State.LoadingState -> {
                                emit(State.LoadingState)
                            }
                            is State.ErrorState -> {
                                emit(State.ErrorState(it.exception))
                            }
                        }
                    }

                } else {
                    emit(State.DataState(it.get(0)))
                }
            }
        }
    }
}

