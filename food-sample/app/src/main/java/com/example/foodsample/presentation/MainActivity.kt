package com.example.foodsample.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodsample.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    @Composable
    private fun RenderScreen() {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(Color(0xFFF2F2F2))
        ) {
            Image(
                painter = painterResource(id = R.drawable.happy_meal_small),
                contentDescription = null,
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Happy Meal",
                        style = TextStyle(
                            fontSize = 26.sp
                        ),
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Text(
                        text = "£5.99",
                        style = TextStyle(
                            fontSize = 17.sp,
                            color = Color(0xFF85bb65)
                        ),
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
                Spacer(modifier = Modifier.padding(top = 10.dp))
                Text(
                    text = "800 Calories",
                    style = TextStyle(
                        fontSize = 17.sp
                    )
                )
                Spacer(modifier = Modifier.padding(top = 14.dp))
                Button(
                    onClick = {},
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "Order Now")
                }
            }
        }
    }

    @Composable
    private fun SecondRenderScreen() {

        Column {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .border(BorderStroke(1.dp, Color.Black)),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "ITEM1",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(
                    text = "ITEM2",
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            Spacer(modifier = Modifier.padding(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .border(BorderStroke(1.dp, Color.Black)),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "ITEM1",
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
    }
}