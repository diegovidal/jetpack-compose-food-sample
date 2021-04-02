package com.example.foodsample.presentation.components

import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

val shimmerColors = listOf(
    Color.LightGray.copy(alpha = 0.9f),
    Color.LightGray.copy(alpha = 0.2f),
    Color.LightGray.copy(alpha = 0.9f)
)

class ShimmerTransitionData(
    xShimmer: State<Float>,
    yShimmer: State<Float>
) {

    val xShimmer by xShimmer
    val yShimmer by yShimmer
}

@Composable
fun updateShimmerTransitionData(
    widthPx: Float,
    heightPx: Float,
    gradientWidth: Float
): ShimmerTransitionData {

    val transition = rememberInfiniteTransition()

    val infiniteRepeatable: InfiniteRepeatableSpec<Float> = infiniteRepeatable(
        tween(durationMillis = 1300, delayMillis = 300, easing = LinearEasing)
    )

    val xShimmer = transition.animateFloat(
        initialValue = 0f,
        targetValue = widthPx + gradientWidth,
        animationSpec = infiniteRepeatable
    )

    val yShimmer = transition.animateFloat(
        initialValue = 0f,
        targetValue = heightPx + gradientWidth,
        animationSpec = infiniteRepeatable
    )

    return remember(transition) { ShimmerTransitionData(xShimmer, yShimmer) }
}