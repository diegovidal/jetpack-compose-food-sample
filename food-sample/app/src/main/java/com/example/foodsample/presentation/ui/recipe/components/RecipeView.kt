package com.example.foodsample.presentation.ui.recipe.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.foodsample.domain.model.Recipe
import com.example.foodsample.other.loadPicture

@Composable
fun RecipeView(
    recipe: Recipe?
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        recipe?.featuredImage?.let { url ->
            loadPicture(url = url).value?.let { img ->
                Image(
                    bitmap = img.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .requiredHeight(270.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 2.dp, start = 8.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                modifier = Modifier
                    .fillMaxWidth(0.85f),
                text = recipe?.title ?: "",
                style = MaterialTheme.typography.h3
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = recipe?.rating.toString(),
                style = MaterialTheme.typography.h5,
                textAlign = TextAlign.End
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 0.dp, horizontal = 8.dp)
        ) {

            Text(
                modifier = Modifier.padding(bottom = 12.dp),
                text = "Updated ${recipe?.dateUpdated} by ${recipe?.publisher}",
                style = MaterialTheme.typography.caption
            )

            recipe?.ingredients?.forEach { ingredient ->
                Text(
                    modifier = Modifier.padding(bottom = 4.dp),
                    text = ingredient,
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}