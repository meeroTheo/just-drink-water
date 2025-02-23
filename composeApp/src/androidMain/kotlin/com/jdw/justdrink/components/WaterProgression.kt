package com.jdw.justdrink.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@SuppressLint("DefaultLocale")
@Composable
fun WaterProgression(
    consumedWater: Int = 1500, //also needs to be changed
    maxWater: Int = 3000, //this will need to be changed
) {
    val progress = consumedWater.toFloat() / maxWater
    val remainingWater = maxWater - consumedWater

    val gradientColors = listOf(
        MaterialTheme.colorScheme.primaryContainer,
        MaterialTheme.colorScheme.onSurface
    )

    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column {
            //gradient
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(brush = Brush.horizontalGradient(gradientColors))
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = "Water Intake",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.inverseSurface
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "$consumedWater ml of $maxWater ml",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.inverseSurface
                    )
                }
            }

            //Bottom Half
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceContainerHigh) //secondary dark color
                    .padding(16.dp)
            ) {
                //progression bar (need to make this look better)
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(12.dp),
                    color = MaterialTheme.colorScheme.tertiary, //theme-adaptive progress color
                    trackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                )

                Spacer(modifier = Modifier.height(16.dp))

                //text for remaining water
                Text(
                    text = "You need $remainingWater ml to reach your daily goal",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}