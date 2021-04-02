package com.example.foodsample.presentation.ui.recipe_list

import com.example.foodsample.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.foodsample.BaseApplication
import com.example.foodsample.other.Constants.KEY_RECIPE_ID
import com.example.foodsample.other.SnackbarController
import com.example.foodsample.presentation.ui.recipe_list.components.RecipeList
import com.example.foodsample.presentation.ui.recipe_list.components.SearchAppBar
import com.example.foodsample.presentation.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class RecipeListFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication

    private val snackbarController by lazy { SnackbarController(lifecycleScope) }
    private val viewModel: RecipeListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {

                val isLoading = viewModel.isLoading.value
                val scaffoldState = rememberScaffoldState()

                AppTheme(
                    darkTheme = application.isDark.value,
                    displayProgressBar = isLoading,
                    scaffoldState = scaffoldState
                ) {

                    RenderRecipeListFragment(isLoading, scaffoldState)
                }
            }
        }
    }

    @Composable
    private fun RenderRecipeListFragment(
        isLoading: Boolean,
        scaffoldState: ScaffoldState
    ) {

        val recipes = viewModel.recipes.value
        val searchQuery = viewModel.searchQuery.value
//        val _searchQuery = rememberSaveable { mutableStateOf("beef") } //ANOTHER APPROACH
        val selectedCategory = viewModel.selectedCategory.value

        val page = viewModel.page.value

        Scaffold(
            scaffoldState = scaffoldState,
            snackbarHost = { scaffoldState.snackbarHostState },
            topBar = {
                SearchAppBar(
                    searchQuery = searchQuery,
                    categoryScrollPosition = viewModel.categoryScrollPosition,
                    selectedCategory = selectedCategory,
                    onQueryChanged = viewModel::onSearchQueryChanged,
                    onSearchRecipes = {

                        if (viewModel.selectedCategory.value?.value == "Milk")
                            snackbarController.getScope().launch {
                                snackbarController.showSnackbar(
                                    scaffoldState = scaffoldState,
                                    message = "Invalid category: MILK!",
                                    actionLabel = "Hide"
                                )
                            }
                        else viewModel.onTriggerEvent(RecipeListEvent.FetchSearchEvent)
                    },
                    onToggleTheme = application::toggleTheme,
                    onSelectedCategoryChange = viewModel::onSelectedCategoryChanged
                )
            },
            bottomBar = {
//                CustomBottomBar()
            },
            drawerContent = {
                CustomDrawer()
            }
        ) {

            RecipeList(
                isLoading = isLoading,
                recipes = recipes,
                scaffoldState = scaffoldState,
                verifyNextPageSearchEvent = { index ->
                    viewModel.onTriggerEvent(RecipeListEvent.VerifyNextPageSearchEvent(index))
                },
                onClickRecipeCard = { recipeId ->
                    Bundle().apply {
                        putInt(KEY_RECIPE_ID, recipeId ?: -1)
                        findNavController().navigate(
                            R.id.action_recipeListFragment_to_recipeFragment,
                            this
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun CustomSnackbar(
    isShowing: Boolean,
    onHideSnackbar: () -> Unit
) {

    if (isShowing) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
        ) {

            val snackbar = createRef()
            Snackbar(
                modifier = Modifier.constrainAs(snackbar) {
                    bottom.linkTo(parent.bottom)
                    centerHorizontallyTo(parent)
                },
                action = {
                    Text(
                        text = "Hide",
                        modifier = Modifier.clickable(onClick = onHideSnackbar),
                        style = MaterialTheme.typography.h5
                    )
                }
            ) { Text(text = "Hey look a snackbar") }
        }
    }
}

@Composable
fun CustomBottomBar() {

    BottomNavigation(
        elevation = 12.dp
    ) {

        BottomNavigationItem(selected = false, onClick = {}, icon = {
            Icon(imageVector = Icons.Default.BrokenImage, contentDescription = null)
        })
        BottomNavigationItem(selected = true, onClick = {}, icon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        })
        BottomNavigationItem(selected = false, onClick = {}, icon = {
            Icon(imageVector = Icons.Default.AccountBalanceWallet, contentDescription = null)
        })
    }
}

@Preview
@Composable
fun CustomDrawer() {

    Column {
        Text(text = "Item1")
        Text(text = "Item2")
        Text(text = "Item3")
        Text(text = "Item4")
        Text(text = "Item5")
    }
}