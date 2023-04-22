package com.mexicandeveloper.upaxpruebatecnica.data.entities

import com.google.gson.annotations.SerializedName

data class PokemonResponse(

    @SerializedName("base_experience") var baseExperience: Int? = null,
    @SerializedName("height") var height: Int? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("order") var order: Int? = null,
    @SerializedName("sprites") var sprites: Sprites? = Sprites(),
    @SerializedName("types") var types: List<Types> = emptyList<Types>(),
    @SerializedName("weight") var weight: Int? = null

)

