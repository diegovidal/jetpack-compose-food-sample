package com.example.foodsample.presentation.ui.recipe_list.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp

@Composable
fun LoadingRecipeListShimmer(
    cardHeight: Dp
) {

    LazyColumn {

        itemsIndexed(
            items = (1..5).toList()
        ) { _, _ ->
            ShimmerRecipeCardItem(
                cardHeight = cardHeight
            )
        }
    }
}