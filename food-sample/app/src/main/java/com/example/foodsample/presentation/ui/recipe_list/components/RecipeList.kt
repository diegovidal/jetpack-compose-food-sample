package com.example.foodsample.presentation.ui.recipe_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.foodsample.domain.model.Recipe
import com.example.foodsample.presentation.ui.recipe_list.components.LoadingRecipeListShimmer
import com.example.foodsample.presentation.ui.recipe_list.components.RecipeCard

@Composable
fun RecipeList(
    isLoading: Boolean,
    recipes: List<Recipe>,
    scaffoldState: ScaffoldState,
    verifyNextPageSearchEvent: (Int) -> Unit,
    onClickRecipeCard: (Int?) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {

        if (isLoading && recipes.isEmpty()) {
            LoadingRecipeListShimmer(250.dp)
        } else {
            LazyColumn {
                itemsIndexed(
                    items = recipes
                ) { index, recipe ->

                    verifyNextPageSearchEvent.invoke(index)
                    RecipeCard(recipe = recipe) { onClickRecipeCard.invoke(recipe.id) }
                }
            }
        }
    }
}