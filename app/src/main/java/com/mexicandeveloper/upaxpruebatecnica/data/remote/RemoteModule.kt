package com.mexicandeveloper.upaxpruebatecnica.data.remote

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    @Provides
    fun bindPokemonRemoteRepository(pokemonRemoteRepositoryImpl: PokemonRemoteRepositoryImpl): PokemonRemoteRepository {
        return pokemonRemoteRepositoryImpl
    }
}