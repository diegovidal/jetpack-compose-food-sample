package com.example.foodsample.presentation.ui.recipe_list.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.foodsample.presentation.ui.recipe_list.FoodCategory
import com.example.foodsample.presentation.ui.recipe_list.components.FoodCategoryChip
import com.example.foodsample.presentation.ui.recipe_list.fetchAllFoodCategories
import kotlinx.coroutines.launch

@Composable
fun SearchAppBar(
    searchQuery: String,
    categoryScrollPosition: Int,
    selectedCategory: FoodCategory?,
    onQueryChanged: (String) -> Unit,
    onSearchRecipes: () -> Unit,
    onToggleTheme: () -> Unit,
    onSelectedCategoryChange: (String, Int) -> Unit
) {

    val scope = rememberCoroutineScope()

    val focusManager = LocalFocusManager.current
    val scrollState = rememberScrollState()

    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = MaterialTheme.colors.surface,
        elevation = 8.dp
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(8.dp),
                    value = searchQuery,
                    onValueChange = onQueryChanged::invoke,
                    label = {
                        Text(text = "Search")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Search,
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            onSearchRecipes.invoke()
                            focusManager.clearFocus()
                        }
                    ),
                    leadingIcon = {
                        Icon(imageVector = Icons.Filled.Search, contentDescription = null)
                    },
                    textStyle = MaterialTheme.typography.h5,
                )

                ConstraintLayout(
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {

                    val menu = createRef()
                    IconButton(
                        modifier = Modifier.constrainAs(menu) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        },
                        onClick = onToggleTheme
                    ) {
                        Icon(imageVector = Icons.Filled.MoreVert, contentDescription = null)
                    }
                }
            }

            Row(
                modifier = Modifier
                    .padding(start = 8.dp, bottom = 8.dp)
                    .horizontalScroll(scrollState)
                    .fillMaxWidth()
            ) {
                for (category in fetchAllFoodCategories()) {
                    FoodCategoryChip(
                        category = category.value,
                        isSelected = selectedCategory == category,
                        onSelectedCategoryChange = {
                            onSelectedCategoryChange.invoke(
                                it,
                                scrollState.value
                            )
                        },
                        onCategoryClick = onSearchRecipes::invoke
                    )
                }

                // Define scroll position
                scope.launch { scrollState.scrollTo(categoryScrollPosition) }
            }
        }
    }
}