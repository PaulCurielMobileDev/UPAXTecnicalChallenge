package com.mexicandeveloper.upaxpruebatecnica.data.entities

data class PokemonListResponse(
    var count: Int,
    var next: String,
    var previous: String,
    var results: List<Pokemon>
)