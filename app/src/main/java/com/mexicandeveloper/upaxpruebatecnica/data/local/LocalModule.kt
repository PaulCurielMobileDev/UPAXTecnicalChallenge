package com.mexicandeveloper.upaxpruebatecnica.data.local

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    fun bindPokemonLocalRepository(pokemonLocalRepositoryImpl: PokemonLocalRepositoryImpl): PokemonLocalRepository {
        return pokemonLocalRepositoryImpl
    }
}
