package com.mexicandeveloper.upaxpruebatecnica.ui.pokemon

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mexicandeveloper.upaxpruebatecnica.data.entities.Pokemon
import com.mexicandeveloper.upaxpruebatecnica.domain.GetPokemonListUseCase
import com.mexicandeveloper.upaxpruebatecnica.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(private val useCase: GetPokemonListUseCase) :
    ViewModel() {

    private val _uiState: MutableStateFlow<State<List<Pokemon>>> =
        MutableStateFlow(State.LoadingState)
    val uiState: StateFlow<State<List<Pokemon>>> = _uiState

    private val pokemons: MutableList<Pokemon> = mutableListOf()

    init {
        Log.d("Soliciting Pokemons", "0<<<<<<<<<<<<<<<<<<<<<")
        getPokemonListResponse(0)
    }

    private fun getPokemonListResponse(offset: Int = 0) {
        Log.d("NumOfPokemons", "${pokemons.size}<<<<<<<<<<<<<<<<<<<<<")
        if (pokemons.size > offset) _uiState.value = State.DataState(pokemons)
        else {
            viewModelScope.launch {

                useCase(offset).collect {
                    if (it is State.DataState) {
                        it.data.let { newPokemons ->
                            pokemons.addAll(newPokemons)
                            Log.d("NumOfPokemons", "${pokemons.size}<<<<<<<<<<<<<<<<<<<<<")
                            _uiState.value = State.DataState(pokemons)
                        }
                    }else{
                        _uiState.value = it
                    }


                }
            }
        }


        /*flow {

            _uiState.value = State.LoadingState

            try {

                if (pokemons.size == offset) {
                    val newPokemons = useCase(offset)
                    pokemons.addAll(newPokemons)
                    _uiState.value=State.DataState(newPokemons)
                } else {
                    _uiState.value=State.DataState(pokemons)
                }

            } catch (e: java.lang.Exception) {
                _uiState.value=State.ErrorState(e)
            }


    }*/
    }

    fun addPokemon(offset: Int) {
        Log.d("SolicitingPokemons", "${offset}<<<<<<<<<<<<<<<<<<<<<")
        if (uiState.value !is State.LoadingState) {
            getPokemonListResponse(offset)
        }
    }
}