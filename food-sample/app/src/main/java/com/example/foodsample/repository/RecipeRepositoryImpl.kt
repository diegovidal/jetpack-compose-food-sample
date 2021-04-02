package com.example.foodsample.repository

import com.example.foodsample.domain.model.Recipe
import com.example.foodsample.network.RecipeNetworkService
import com.example.foodsample.network.model.RecipeDtoMapper
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val networkService: RecipeNetworkService,
    private val mapper: RecipeDtoMapper
): RecipeRepository {

    override suspend fun searchRecipes(token: String, page: Int, query: String): List<Recipe> {
        return mapper.toDomainList(networkService.searchRecipes(token, page, query).recipes)
    }

    override suspend fun fetchRecipe(token: String, id: Int): Recipe {
        return mapper.mapToDomainModel(networkService.fetchRecipe(token, id))
    }
}