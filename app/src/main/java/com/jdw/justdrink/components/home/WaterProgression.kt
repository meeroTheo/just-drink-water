package com.jdw.justdrink.components.home

import android.annotation.SuppressLint
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.sin

@SuppressLint("DefaultLocale")
@Composable
fun WaterProgression(
    consumedWater: Int = 0, //also needs to be changed
    maxWater: Int = 3000, //this will need to be changed
) {
    val progress = consumedWater.toFloat() / maxWater
    val remainingWater = maxWater - consumedWater

    val gradientColors = listOf(
        MaterialTheme.colorScheme.primaryContainer,
        MaterialTheme.colorScheme.onSurface
    )

    //infinite gradient animation
    val infiniteTransition = rememberInfiniteTransition()
    val time by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2 * Math.PI.toFloat(), //Full sine wave cycle
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 8000, easing = LinearEasing), // Slower animation (8 seconds)
            repeatMode = RepeatMode.Reverse // Smoothly reverse the animation
        )
    )

    //motion of the gradient (gas like)
    val offsetX1 = 300 * sin(time).toFloat() //first wave
    val offsetX2 = 200 * cos(time * 1.5f).toFloat()
    val offsetX3 = 100 * sin(time * 0.8f).toFloat()

    //combining the waves
    val combinedOffsetX = offsetX1 + offsetX2 + offsetX3

    //moving gradient brush
    val gradientBrush = Brush.horizontalGradient(
        colors = gradientColors,
        startX = -1000 + combinedOffsetX, // Apply the combined offset
        endX = 1000 + combinedOffsetX
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
//            .border(
//                width = 2.dp,
//                color = MaterialTheme.colorScheme.primary, // You can change the color here
//                shape = RoundedCornerShape(20.dp)
//            )
    ) {
        Card(
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column {
                //Gradient box
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(brush = gradientBrush) // Apply the animated gradient
                        .padding(16.dp)
                ) {
                    //text on Top of the Gradient
                    Column {
                        Text(
                            text = "Water Intake",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.White // Set text color to white
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "$consumedWater ml of $maxWater ml",
                            fontSize = 16.sp,
                            color = Color.White // Set text color to white
                        )
                    }
                }

                //bottom Half
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surfaceContainerLow) //secondary dark color
                        .padding(16.dp)
                ) {
                    //progression bar (need to make this look better)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(14.dp)
                            .clip(RoundedCornerShape(6.dp)) // Rounded corners for the progress bar
                            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)) // Track color
                    ) {
                        // Progress Indicator
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(progress) // Set width based on progress
                                .height(14.dp)
                                .clip(RoundedCornerShape(6.dp)) // Rounded corners for the progress
                                .background(MaterialTheme.colorScheme.onSurface) // Progress color
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = if (consumedWater < maxWater) {
                            "You need $remainingWater ml to reach your daily goal"
                        } else {
                            "You've reached your daily water goal!"
                        },
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}