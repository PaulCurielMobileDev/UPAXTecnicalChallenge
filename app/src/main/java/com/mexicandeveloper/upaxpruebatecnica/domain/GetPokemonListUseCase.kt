package com.mexicandeveloper.upaxpruebatecnica.domain

import com.mexicandeveloper.upaxpruebatecnica.data.PokemonAPI
import com.mexicandeveloper.upaxpruebatecnica.utils.State
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPokemonListUseCase @Inject constructor(private val api: PokemonAPI) {

    suspend operator fun invoke(offset: Int)= flow{

        emit(State.LoadingState)
        try {
            val response = api.getPokemonList(offset, 25)
            emit(State.DataState(response.results))
        }catch (e:Exception){
            emit(State.ErrorState(e))
        }

    }
}