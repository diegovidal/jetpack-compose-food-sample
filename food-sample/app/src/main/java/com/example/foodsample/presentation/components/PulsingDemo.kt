package com.example.foodsample.presentation.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview
@Composable
fun PulsingDemo() {

    val color = MaterialTheme.colors.primary
    val infiniteTransition = rememberInfiniteTransition()

    val pulseMagnitude by infiniteTransition.animateFloat(
        initialValue = PulseState.INITIAL.value,
        targetValue = PulseState.FINAL.value,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .height(pulseMagnitude.dp)
                .width(pulseMagnitude.dp),
            imageVector = Icons.Default.Favorite,
            colorFilter = ColorFilter.tint(Color.Red),
            contentDescription = null
        )

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        ) {

            drawCircle(
                radius = pulseMagnitude,
                brush = SolidColor(color)
            )
        }
    }


}


enum class PulseState(val value: Float) {
    INITIAL(40f), FINAL(50f)
}
