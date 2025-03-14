package com.jdw.justdrink.components.settings

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jdw.justdrink.helper.SharedPreferencesHelper
import com.jdw.justdrink.notifications.NotificationScheduler
@Composable
fun NotificationSelector(context: Context) {
    val prefs = remember { SharedPreferencesHelper(context) }

    var frequency by remember { mutableIntStateOf(prefs.getReminderFrequency()) }
    var sleepStart by remember { mutableIntStateOf(prefs.getSleepStart()) }
    var sleepEnd by remember { mutableIntStateOf(prefs.getSleepEnd()) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Reminder Frequency (Minutes)")
        Slider(
            value = frequency.toFloat(),
            onValueChange = {
                frequency = it.toInt()
                prefs.saveReminderFrequency(frequency)
                NotificationScheduler.scheduleNotifications(context)
            },
            valueRange = 15f..180f,
            steps = 10
        )
        Text("$frequency minutes")

        Spacer(modifier = Modifier.height(16.dp))

        Text("Sleep Start Time (Hour in 24h Format)")
        Slider(
            value = sleepStart.toFloat(),
            onValueChange = {
                sleepStart = it.toInt()
                prefs.saveSleepSchedule(sleepStart, sleepEnd)
            },
            valueRange = 0f..23f
        )
        Text("$sleepStart:00")

        Spacer(modifier = Modifier.height(16.dp))

        Text("Sleep End Time (Hour in 24h Format)")
        Slider(
            value = sleepEnd.toFloat(),
            onValueChange = {
                sleepEnd = it.toInt()
                prefs.saveSleepSchedule(sleepStart, sleepEnd)
            },
            valueRange = 0f..23f
        )
        Text("$sleepEnd:00")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            NotificationScheduler.scheduleNotifications(context)
        }) {
            Text("Reschedule Reminders")
        }
    }
}
