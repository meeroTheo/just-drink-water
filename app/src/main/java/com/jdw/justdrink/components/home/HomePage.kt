package com.jdw.justdrink.components.home


import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jdw.justdrink.data.IntakeViewModel
import com.jdw.justdrink.helper.SharedPreferencesHelper
import kotlinx.coroutines.launch


@Composable
fun HomePage(viewModel: IntakeViewModel, context: Context) {
    val coroutineScope = rememberCoroutineScope()
    var totalIntake by remember { mutableIntStateOf(0) }
    val prefs = remember { SharedPreferencesHelper(context) }
    val userName by remember { mutableStateOf(prefs.getUserName() ?: "User") }


    //get todays intake from viewmodel
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            val intakeRecord = viewModel.getTodayIntake() //Retrieve from DB
            totalIntake = intakeRecord ?: 0
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {

        // Header Section
        Column(modifier = Modifier.padding(top = 80.dp, start = 16.dp)) {
            Text(
                text = "Welcome",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = userName.lowercase(), // Replace with actual user name
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold), // Large, bold
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

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