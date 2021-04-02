package com.example.foodsample.presentation.ui.recipe_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.foodsample.presentation.components.shimmerColors
import com.example.foodsample.presentation.components.updateShimmerTransitionData


@Composable
fun ShimmerRecipeCardItem(
    cardHeight: Dp,
    colors: List<Color> = shimmerColors,
    padding: Dp = 16.dp
) {

    BoxWithConstraints {

        val cardWidthPx = with(LocalDensity.current) {
            (maxWidth - (padding * 2)).toPx()
        }

        val cardHeightPx = with(LocalDensity.current) {
            (cardHeight - padding).toPx()
        }

        val gradientWidth = 0.2f * cardHeightPx

        val transitionData = updateShimmerTransitionData(
            widthPx = cardWidthPx,
            heightPx = cardHeightPx,
            gradientWidth = gradientWidth
        )

        val brush = Brush.linearGradient(
            colors = colors,
            start = Offset(
                transitionData.xShimmer - gradientWidth,
                transitionData.yShimmer - gradientWidth
            ),
            end = Offset(transitionData.xShimmer, transitionData.yShimmer)
        )

        Column(
            modifier = Modifier.padding(padding)
        ) {
            Surface(shape = MaterialTheme.shapes.small) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .requiredHeight(cardHeight)
                        .background(brush)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Surface(shape = MaterialTheme.shapes.small) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .requiredHeight(cardHeight / 10)
                        .background(brush)
                )
            }
        }
    }
}