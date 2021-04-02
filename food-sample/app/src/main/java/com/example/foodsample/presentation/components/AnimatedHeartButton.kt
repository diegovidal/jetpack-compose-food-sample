package com.example.foodsample.presentation.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

sealed class HeartState(val isLiked: Boolean) {

    data class Collapsed(val liked: Boolean): HeartState(liked)
    data class Expanded(val liked: Boolean): HeartState(liked)
}

@Preview
@Composable
fun HeartAnimationContainer() {

    val heartState = remember<MutableState<HeartState>> { mutableStateOf(HeartState.Collapsed(false)) }

    Column(
        modifier = Modifier
            .padding(24.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedHearButton(
            heartState = heartState.value,
            onHeartClick = {
                val isLiked = heartState.value.isLiked.not()
                heartState.value = HeartState.Expanded(isLiked)
            },
            onAnimationFinished = {
                val isLiked = heartState.value.isLiked
                heartState.value = HeartState.Collapsed(isLiked)
            }
        )
    }
}

@Composable
fun AnimatedHearButton(
    heartState: HeartState,
    onHeartClick: () -> Unit,
    onAnimationFinished: () -> Unit
) {

    val transitionData = updateHeartTransitionData(heartState = heartState)

    Image(
        imageVector = Icons.Default.Favorite,
        contentDescription = null,
        colorFilter = ColorFilter.tint(transitionData.color),
        modifier = Modifier
            .height(transitionData.size.dp)
            .width(transitionData.size.dp)
            .clickable(onClick = onHeartClick)
            .animateContentSize(animationSpec = tween(durationMillis = 20, easing = FastOutSlowInEasing), finishedListener = { _, _ ->
                onAnimationFinished.invoke()
            })
    )
}

private class HeartTransitionData(
    color: State<Color>,
    size: State<Float>
) {

    val color by color
    val size by size
}

@Composable
private fun updateHeartTransitionData(heartState: HeartState): HeartTransitionData {

    val transition = updateTransition(heartState, label = "heartState")

    val color = transition.animateColor(label = "animateColor") { state ->
        if (state.isLiked ) Color.Red else Color.LightGray
    }

    val size = transition.animateFloat(label = "animateDp") { state ->
        when (state) {
            is HeartState.Collapsed -> 80f
            is HeartState.Expanded -> 100f
        }
    }

    return remember(transition) { HeartTransitionData(color, size) }
}