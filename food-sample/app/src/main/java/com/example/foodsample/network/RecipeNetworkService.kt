package com.example.foodsample.network

import com.example.foodsample.network.model.RecipeDto
import com.example.foodsample.network.responses.RecipeSearchResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RecipeNetworkService {

    @GET("search")
    suspend fun searchRecipes(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("query") query: String
    ): RecipeSearchResponse

    @GET("get")
    suspend fun fetchRecipe(
        @Header("Authorization") token: String,
        @Query("id") id: Int
    ): RecipeDto

}