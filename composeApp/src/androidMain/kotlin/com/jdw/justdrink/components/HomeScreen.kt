package com.jdw.justdrink.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.jdw.justdrink.components.BottomNavigationBar
import com.jdw.justdrink.components.WaterProgression

@Composable
fun HomeScreen() {
    Scaffold (
        bottomBar = { BottomNavigationBar()}
    ){ paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            HomeContent()
            WaterProgression()
        }
    }
}

@Composable
fun HomeContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
        Text(text = "Home Screen", style = MaterialTheme.typography.headlineMedium)
    }
}

@Composable
fun SettingsContent() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
        Text(text = "Settings Screen", style = MaterialTheme.typography.headlineMedium)
    }
}