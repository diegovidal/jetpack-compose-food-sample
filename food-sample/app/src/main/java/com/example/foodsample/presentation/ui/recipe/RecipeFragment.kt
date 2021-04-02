package com.example.foodsample.presentation.ui.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.foodsample.BaseApplication
import com.example.foodsample.R
import com.example.foodsample.other.Constants.KEY_RECIPE_ID
import com.example.foodsample.presentation.ui.recipe.components.RecipeShimmerView
import com.example.foodsample.presentation.ui.recipe.components.RecipeView
import com.example.foodsample.presentation.theme.AppTheme
import com.example.foodsample.presentation.ui.recipe.RecipeEvent.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecipeFragment : Fragment(R.layout.fragment_recipe) {

    @Inject
    lateinit var application: BaseApplication

    private val viewModel by viewModels<RecipeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.getInt(KEY_RECIPE_ID)?.let { id ->
            viewModel.onTriggerEvent(FetchRecipeEvent(id))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                val isLoading = viewModel.isLoading.value

                AppTheme(
                    darkTheme = application.isDark.value,
                    displayProgressBar = isLoading,
                    scaffoldState = rememberScaffoldState()
                ) {

                    RenderRecipeFragment(isLoading)
                }
            }
        }
    }

    @Composable
    fun RenderRecipeFragment(
        isLoading: Boolean
    ) {

        val recipe = viewModel.recipe.value

        Box(modifier = Modifier.fillMaxSize()) {

            if (isLoading && recipe == null) {
                RecipeShimmerView(
                    imageHeight = 260.dp
                )
            } else RecipeView(recipe = recipe)
        }
    }
}