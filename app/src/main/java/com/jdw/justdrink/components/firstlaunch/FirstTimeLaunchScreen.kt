package com.jdw.justdrink.components.firstlaunch

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jdw.justdrink.helper.SharedPreferencesHelper
import com.jdw.justdrink.helper.TimePickerDialog
import kotlinx.coroutines.launch


@Composable
fun FirstTimeLaunchScreen(onSetupComplete: () -> Unit, context: Context) {
    val prefs = remember { SharedPreferencesHelper(context) }
    val pagerState = rememberPagerState(pageCount = { 4 })
    val coroutineScope = rememberCoroutineScope()
    var name by remember { mutableStateOf("") }
    var sleepStart by remember { mutableIntStateOf(23) }
    var sleepEnd by remember { mutableIntStateOf(7) }
    var reminderFrequency by remember { mutableIntStateOf(60) }

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surfaceContainerLowest)) {
        Column(modifier = Modifier.fillMaxSize().padding(bottom = 80.dp)) {
            HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { page ->
                when (page) {
                    0 -> NameScreen(name) { name = it }
                    1 -> SleepScreen(sleepStart) { sleepStart = it }
                    2 -> WakeUpScreen(sleepEnd) { sleepEnd = it }
                    3 -> ReminderScreen(reminderFrequency) { reminderFrequency = it }
                }
            }
        }


        Box(modifier = Modifier.fillMaxSize()) {

            if (pagerState.currentPage > 0) {
                FloatingActionButton(
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(pagerState.currentPage - 1) } },
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp),
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.secondary
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }


            FloatingActionButton(
                onClick = {
                    if (pagerState.currentPage < 3) {
                        coroutineScope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                    } else {
                        prefs.saveUserName(name) // Save the user's name
                        prefs.saveSleepSchedule(sleepStart, sleepEnd)
                        prefs.saveReminderFrequency(reminderFrequency)
                        prefs.setFirstLaunchDone()
                        onSetupComplete()
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.secondary
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = if (pagerState.currentPage == 3) "Submit" else "Next"
                )
            }

        }
    }
}



@Composable
fun NameScreen(name: String, onNameChange: (String) -> Unit) {
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = Icons.Default.LocalDrink,
            contentDescription = "Water Cup",
            tint = Color.White,
            modifier = Modifier
                .size(250.dp)
                .padding(top = 100.dp)
        )
        Text(
            text = "Welcome to Just Drink Water!",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 80.dp,bottom = 23.dp)
        )
        Text(
            text = "What's your name?", textAlign = TextAlign.Center, color = Color.White, fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 20.dp)
        )
        OutlinedTextField(value = name, onValueChange = onNameChange, label = { Text("Name") })
    }
}

@Composable
fun SleepScreen(sleepStart: Int, onSleepChange: (Int) -> Unit) {
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = Icons.Default.Bedtime,
            contentDescription = "Water Cup",
            tint = Color.White,
            modifier = Modifier
                .size(250.dp)
                .padding(top = 100.dp)
        )

        Text(
            text = "What time do you go to bed?", textAlign = TextAlign.Center, color = Color.White, fontSize = 18.sp,
            modifier = Modifier.padding(top = 139.dp, bottom = 20.dp)
        )
        SleepTimeSelector(sleepStart, onSleepChange)
    }
}

@Composable
fun WakeUpScreen(sleepEnd: Int, onWakeChange: (Int) -> Unit) {

    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = Icons.Default.WbSunny,
            contentDescription = "Water Cup",
            tint = Color.White,
            modifier = Modifier
                .size(250.dp)
                .padding(top = 100.dp)
        )
        Text(
            text = "What time do you wake up?", textAlign = TextAlign.Center, color = Color.White, fontSize = 18.sp,
            modifier = Modifier.padding(top = 139.dp, bottom = 20.dp)
        )
        SleepTimeSelector(sleepEnd, onWakeChange)
    }
}

@Composable
fun ReminderScreen(reminderFrequency: Int, onFrequencyChange: (Int) -> Unit) {
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {

        Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = "Water Cup",
            tint = Color.White,
            modifier = Modifier
                .size(250.dp)
                .padding(top = 100.dp)
        )
        Text(
            text = "How often do you want notifications?", textAlign = TextAlign.Center, color = Color.White, fontSize = 18.sp,
            modifier = Modifier.padding(top = 139.dp, bottom = 20.dp)
        )
        ReminderFrequencySelector(reminderFrequency, onFrequencyChange)
    }
}


@Composable
fun SleepTimeSelector(selectedHour: Int, onTimeSelected: (Int) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }
    var hour by remember { mutableIntStateOf(selectedHour) }
    var minute by remember { mutableIntStateOf(0) }

    Button(
        onClick = { showDialog = true },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary
        )

    ) {
        Text("$hour:${minute.toString().padStart(2, '0')}")
    }

    if (showDialog) {
        TimePickerDialog(
            onDismissRequest = { showDialog = false },
            onConfirm = { newHour, newMinute ->
                hour = newHour
                minute = newMinute
                onTimeSelected(hour)
            },
            initialHour = hour,
            initialMinute = minute
        )
    }
}


@Composable
fun ReminderFrequencySelector(selectedFrequency: Int, onFrequencySelected: (Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val options = listOf(15, 30, 45, 60, 90, 120, 180)

    Box {
        Button(
            onClick = { expanded = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ),


        ) { Text("$selectedFrequency min") }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(text = { Text("$option min") }, onClick = {
                    onFrequencySelected(option)
                    expanded = false
                })
            }
        }
    }
}