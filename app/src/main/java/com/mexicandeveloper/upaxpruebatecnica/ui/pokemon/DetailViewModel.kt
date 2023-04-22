package com.mexicandeveloper.upaxpruebatecnica.ui.pokemon

import androidx.lifecycle.ViewModel
import com.mexicandeveloper.upaxpruebatecnica.domain.GetPokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val useCase: GetPokemonUseCase):ViewModel() {

    suspend fun getPokemon(pokemonNumber: Int) = useCase.invoke(pokemonNumber)
}