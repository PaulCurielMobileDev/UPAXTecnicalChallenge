package com.mexicandeveloper.upaxpruebatecnica.ui.pokemon

import androidx.lifecycle.ViewModel
import com.mexicandeveloper.upaxpruebatecnica.data.entities.Pokemon
import com.mexicandeveloper.upaxpruebatecnica.domain.GetPokemonListUseCase
import com.mexicandeveloper.upaxpruebatecnica.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(private val useCase: GetPokemonListUseCase) :ViewModel() {

    fun getPokemonListResponse() = flow<State<List<Pokemon>>> {

         emit(State.LoadingState)

        try {
            emit(State.DataState(useCase(25)))
        }catch (e:java.lang.Exception){
            emit(State.ErrorState(e))
        }

     }
    }
