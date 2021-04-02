package com.example.foodsample.presentation.ui.recipe

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodsample.domain.model.Recipe
import com.example.foodsample.other.Constants
import com.example.foodsample.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val repository: RecipeRepository,
    @Named("auth_token") private val token: String,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    val recipe = mutableStateOf<Recipe?>(null)
    val isLoading = mutableStateOf<Boolean>(false)

    init {
        // restore if process dies
        savedStateHandle.get<Int>(Constants.STATE_KEY_RECIPE_ID)?.let {
            onTriggerEvent(RecipeEvent.FetchRecipeEvent(it))
        }
    }

    fun onTriggerEvent(event: RecipeEvent)  = viewModelScope.launch {

        try {
            when(event) {
                is RecipeEvent.FetchRecipeEvent -> fetchRecipe(event.id)
            }
        } catch (e: Exception) {
            Log.e(Constants.DEBUG_TAG, "onTriggerEvent Exception: $e, ${e.cause}")
        }
    }

    private suspend fun fetchRecipe(id: Int)  {

        if (recipe.value == null) {
            isLoading.value = true
            val result = repository.fetchRecipe(
                token = token,
                id = id
            )

            recipe.value = result
            savedStateHandle.set(Constants.STATE_KEY_RECIPE_ID, result.id)

            isLoading.value = false
        }
    }
}