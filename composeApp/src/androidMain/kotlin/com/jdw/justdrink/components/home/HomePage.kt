package com.jdw.justdrink.components.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun HomePage() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        WaterProgression()

        WaterIntakeButtons(
            onBottleSelected = { size -> /*placeholder for event handler (later on)*/ },
            customSize = null //start without custom size
        )
    }
}
