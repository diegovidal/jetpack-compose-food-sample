package com.example.foodsample.presentation.components

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet

@Composable
fun CircularIndeterminateProgressBar(
    isDisplayed: Boolean
) {

    if (isDisplayed) {

        BoxWithConstraints(
            modifier = Modifier.fillMaxSize()
        ) {

            val constraints = if (minWidth < 600.dp) { // portrait
                decoupledConstraints(0.3f)
            } else {
                decoupledConstraints(0.7f)
            }

            ConstraintLayout(
                modifier = Modifier.fillMaxSize(),
                constraintSet = constraints
            ) {

                CircularProgressIndicator(
                    modifier = Modifier.layoutId("progressBar"),
                    color = MaterialTheme.colors.primary
                )

//                Text(
//                    modifier = Modifier.layoutId("text"),
//                    text = "Loading...",
//                    style = TextStyle(
//                        color = Color.Black,
//                        fontSize = 15.sp
//                    )
//                )
            }
        }
    }
}

private fun decoupledConstraints(verticalBias: Float): ConstraintSet {

    return ConstraintSet {

        val guideline = createGuidelineFromTop(verticalBias)
        val progressBarRef = createRefFor("progressBar")
        val textRef = createRefFor("text")

        constrain(progressBarRef) {
            top.linkTo(guideline)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(textRef) {
            top.linkTo(progressBarRef.bottom, 12.dp)
            centerHorizontallyTo(parent)
        }
    }
}