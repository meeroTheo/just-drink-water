package com.jdw.justdrink.components.home


import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.jdw.justdrink.data.IntakeViewModel
import kotlinx.coroutines.launch


@Composable
fun HomePage(viewModel: IntakeViewModel) {
    val coroutineScope = rememberCoroutineScope()
    var totalIntake by remember { mutableIntStateOf(0) }

    //get todays intake from viewmodel
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val intakeRecord = viewModel.getTodayIntake() //Retrieve from DB
            totalIntake = intakeRecord ?: 0
        }
    }

    //get context for custom size persistence
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        //need to give only today's total intake to waterprogression, not lifetime
        WaterProgression(totalIntake)


        WaterIntakeButtons(
            context = context,
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