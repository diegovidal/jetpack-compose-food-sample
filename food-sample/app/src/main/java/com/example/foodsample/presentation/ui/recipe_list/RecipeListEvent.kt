package com.example.foodsample.presentation.ui.recipe_list

sealed class RecipeListEvent {

    object FetchSearchEvent: RecipeListEvent()
    data class VerifyNextPageSearchEvent(val position: Int): RecipeListEvent()

    // Restore after process death
    object RestoreStateEvent: RecipeListEvent()
}