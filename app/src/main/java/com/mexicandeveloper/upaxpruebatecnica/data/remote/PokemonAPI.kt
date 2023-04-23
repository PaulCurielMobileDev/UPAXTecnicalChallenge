package com.mexicandeveloper.upaxpruebatecnica.data.remote


import com.mexicandeveloper.upaxpruebatecnica.data.models.PokemonListResponse
import com.mexicandeveloper.upaxpruebatecnica.data.models.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonAPI {

    @GET("pokemon/")
    suspend fun getPokemonList(@Query("offset") offset: Int, @Query("limit") limit: Int):PokemonListResponse

    @GET("pokemon/{num}/")
    suspend fun getPokemon(@Path("num")num:Int):PokemonResponse

}