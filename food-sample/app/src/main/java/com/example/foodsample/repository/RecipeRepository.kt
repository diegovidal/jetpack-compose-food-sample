package com.example.foodsample.repository

import com.example.foodsample.domain.model.Recipe

interface RecipeRepository {

    suspend fun searchRecipes(token: String, page: Int, query: String): List<Recipe>
    suspend fun fetchRecipe(token: String, id: Int): Recipe
}