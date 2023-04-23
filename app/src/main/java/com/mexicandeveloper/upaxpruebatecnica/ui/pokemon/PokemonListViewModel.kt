package com.mexicandeveloper.upaxpruebatecnica.ui.pokemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mexicandeveloper.upaxpruebatecnica.data.entities.PokemonEntity
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

    private val _uiState: MutableStateFlow<State<List<PokemonEntity>>> =
        MutableStateFlow(State.LoadingState)
    val uiState: StateFlow<State<List<PokemonEntity>>> = _uiState

    private val pokemons: MutableList<PokemonEntity> = mutableListOf()

    init {
        getPokemonListResponse(0)
    }

    private fun getPokemonListResponse(offset: Int = 0) {
        if (pokemons.size > offset) _uiState.value = State.DataState(pokemons)
        else {
            viewModelScope.launch {

                useCase(offset).collect {
                    if (it is State.DataState) {
                        it.data.let { newPokemons ->
                            pokemons.addAll(newPokemons)
                            _uiState.value = State.DataState(pokemons)
                        }
                    }else{
                        _uiState.value = it
                    }
                }
            }
        }
    }

    fun addPokemon(offset: Int) {
        if (uiState.value !is State.LoadingState) {
            getPokemonListResponse(offset)
        }
    }
}