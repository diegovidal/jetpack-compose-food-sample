package com.example.foodsample.presentation.ui.recipe

sealed class RecipeEvent {

    data class FetchRecipeEvent(val id: Int): RecipeEvent()
}