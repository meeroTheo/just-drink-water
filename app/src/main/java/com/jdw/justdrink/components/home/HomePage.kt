package com.jdw.justdrink.components.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jdw.justdrink.data.IntakeViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomePage(viewModel: IntakeViewModel) {
    val coroutineScope = rememberCoroutineScope()
    var totalIntake by remember { mutableStateOf(0) }

    //get today intake from ViewModel
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val intakeRecord = viewModel.getTodayIntake()
            totalIntake = intakeRecord ?: 0
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        WaterProgression(totalIntake)

        WaterIntakeButtons(
            onBottleSelected = { size ->
                coroutineScope.launch {
                    viewModel.addWaterIntake(size)
                    totalIntake += size
                }
            },
            customSize = null
        )
    }
}
