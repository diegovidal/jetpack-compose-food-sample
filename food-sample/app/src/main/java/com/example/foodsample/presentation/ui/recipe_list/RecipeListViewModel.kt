package com.example.foodsample.presentation.ui.recipe_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodsample.domain.model.Recipe
import com.example.foodsample.other.Constants.DEBUG_TAG
import com.example.foodsample.other.Constants.PAGE_SIZE
import com.example.foodsample.other.Constants.STATE_KEY_LIST_POSITION
import com.example.foodsample.other.Constants.STATE_KEY_PAGE
import com.example.foodsample.other.Constants.STATE_KEY_QUERY
import com.example.foodsample.other.Constants.STATE_KEY_SELECTED_CATEGORY
import com.example.foodsample.presentation.ui.recipe_list.RecipeListEvent.*
import com.example.foodsample.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named


@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val repository: RecipeRepository,
    @Named("auth_token") private val token: String,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())
    val searchQuery = mutableStateOf("")
    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)
    val isLoading = mutableStateOf(false)

    val page = mutableStateOf(1)
    private var recipeListScrollPosition = 0

    var categoryScrollPosition = 0


    init {

        restoreSavedStateVariables()
        if (recipeListScrollPosition != 0)
            onTriggerEvent(RestoreStateEvent)
        else onTriggerEvent(FetchSearchEvent)
    }

    private fun restoreSavedStateVariables() {

        savedStateHandle.get<Int>(STATE_KEY_PAGE)?.let { this.page.value = it }
        savedStateHandle.get<String>(STATE_KEY_QUERY)?.let { this.searchQuery.value = it }
        savedStateHandle.get<Int>(STATE_KEY_LIST_POSITION)
            ?.let { this.recipeListScrollPosition = it }
        savedStateHandle.get<FoodCategory>(STATE_KEY_SELECTED_CATEGORY)
            ?.let { this.selectedCategory.value = it }
    }

    fun onTriggerEvent(event: RecipeListEvent) = viewModelScope.launch {

        try {
            when (event) {
                is FetchSearchEvent -> searchRecipes()
                is VerifyNextPageSearchEvent -> verifyNextRecipeListPage(event.position)
                is RestoreStateEvent -> restoreState()
            }
        } catch (e: Exception) {
            Log.e(DEBUG_TAG, "onTriggerEvent Exception: $e, ${e.cause}")
        }
    }

    // use case #1
    private suspend fun searchRecipes() {

        isLoading.value = true
        resetSearchState()

        val result = repository.searchRecipes(
            token = token,
            page = 1,
            query = searchQuery.value
        )

        recipes.value = result
        isLoading.value = false

    }

    // use case #2
    private suspend fun verifyNextRecipeListPage(position: Int) {

        onChangeRecipeListScrollPosition(position)
        if (recipeListScrollPosition + 1 >= page.value * PAGE_SIZE && !isLoading.value) {
            // prevent duplicate events due to recompose happening too quickly
            isLoading.value = true

            // Just to show pagination, cause api is fast
            delay(1000)

            if (page.value + 1 > 1) {
                val result = repository.searchRecipes(
                    token = token,
                    page = page.value + 1,
                    query = searchQuery.value
                )

                if (result.isNotEmpty()) {
                    incrementRecipeListPage()
                    appendRecipes(result)
                }
            }
            isLoading.value = false
        }
    }

    // use case #3
    private suspend fun restoreState() {

        isLoading.value = true
        val results = mutableListOf<Recipe>()

        for (p in 1..page.value) {
            val result = repository.searchRecipes(
                token = token,
                page = p,
                query = searchQuery.value
            )
            results.addAll(result)
        }

        this.recipes.value = results
        this.isLoading.value = false
    }

    /**
     * Append new recipes to the current list of recipes
     */
    private fun appendRecipes(recipes: List<Recipe>) {
        val currentList = ArrayList(this.recipes.value)
        currentList.addAll(recipes)

        this.recipes.value = currentList
    }

    private fun incrementRecipeListPage() {
        page.value = page.value + 1
        savedStateHandle.set(STATE_KEY_PAGE, page.value)
        Log.d(DEBUG_TAG, "Current page: ${page.value}")
    }

    private fun onChangeRecipeListScrollPosition(position: Int) {
        recipeListScrollPosition = position
        savedStateHandle.set(STATE_KEY_LIST_POSITION, position)
    }

    fun onSearchQueryChanged(query: String) {
        this.searchQuery.value = query
        savedStateHandle.set(STATE_KEY_QUERY, query)
    }

    fun onSelectedCategoryChanged(category: String, position: Int) {
        val newCategory = fetchFoodCategory(category)

        selectedCategory.value = newCategory
        savedStateHandle.set(STATE_KEY_SELECTED_CATEGORY, newCategory)

        onSearchQueryChanged(category)
        this.categoryScrollPosition = position
    }

    private fun resetSearchState() {
        recipes.value = emptyList()

        selectedCategory.value = fetchFoodCategory(searchQuery.value)
        savedStateHandle.set(STATE_KEY_SELECTED_CATEGORY, selectedCategory.value)

        page.value = 1
        savedStateHandle.set(STATE_KEY_PAGE, page.value)

        onChangeRecipeListScrollPosition(0)
    }
}