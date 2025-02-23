package com.jdw.justdrink.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@Composable
fun HomePage() {
    //Home Page
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
        Text(text = "Home Screen", style = MaterialTheme.typography.headlineMedium)
    }
    WaterProgression()
}

