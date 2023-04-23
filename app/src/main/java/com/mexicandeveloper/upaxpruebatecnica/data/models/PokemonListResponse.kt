package com.mexicandeveloper.upaxpruebatecnica.data.models

data class PokemonListResponse(
    var count: Int,
    var next: String,
    var previous: String,
    var results: List<Pokemon>
)