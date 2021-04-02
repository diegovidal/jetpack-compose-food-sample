package com.example.foodsample.network.responses

import com.example.foodsample.network.model.RecipeDto
import com.google.gson.annotations.SerializedName

data class RecipeSearchResponse(

    @SerializedName("count")
    val count: Int,

    @SerializedName("results")
    val recipes: List<RecipeDto>
)