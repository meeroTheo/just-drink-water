package com.jdw.justdrink.components.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jdw.justdrink.data.IntakeViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomePage(viewModel: IntakeViewModel) {
    val coroutineScope = rememberCoroutineScope()
    var totalIntake by remember { mutableIntStateOf(0) }

    // 🔹 Fetch today's intake from ViewModel when the screen is first loaded
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val intakeRecord = viewModel.getTodayIntake() // Retrieve from DB
            totalIntake = intakeRecord ?: 0
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        WaterProgression(totalIntake)

        WaterIntakeButtons(
            onBottleSelected = { size ->
                coroutineScope.launch {
                    viewModel.addWaterIntake(size)
                    totalIntake += size // Update UI instantly
                }
            },
            customSize = null
        )
    }
}
