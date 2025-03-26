package com.jdw.justdrink.components.settings

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jdw.justdrink.helper.SharedPreferencesHelper
import com.jdw.justdrink.helper.TimePickerDialog
import com.jdw.justdrink.notifications.NotificationScheduler
import com.jdw.justdrink.ui.theme.AppTheme
import com.jdw.justdrink.ui.theme.themes

@Composable
fun SettingsPage(context: Context, onThemeChange: (AppTheme) -> Unit) {
    val prefs = remember { SharedPreferencesHelper(context) }
    var showSleepStartDialog by remember { mutableStateOf(false) }
    var showSleepEndDialog by remember { mutableStateOf(false) }
    var showReminderDialog by remember { mutableStateOf(false) }
    var showThemeDialog by remember { mutableStateOf(false) }

    var sleepStart by remember { mutableIntStateOf(prefs.getSleepStart()) }
    var sleepEnd by remember { mutableIntStateOf(prefs.getSleepEnd()) }
    var reminderFrequency by remember { mutableIntStateOf(prefs.getReminderFrequency()) }
    var selectedTheme by remember { mutableStateOf(themes.find { it.name == prefs.getSelectedTheme() } ?: AppTheme.Default) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("General", style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(top = 100.dp, bottom = 20.dp),
            color = MaterialTheme.colorScheme.onSurface)

        SectionTitle("Appearance")
        SettingItem(title = "App Theme", subtitle = selectedTheme.name) {
            showThemeDialog = true
        }

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
        )
        Spacer(modifier = Modifier.height(16.dp))

        SectionTitle("Reminder Frequency")
        SettingItem(title = "Frequency", subtitle = "$reminderFrequency min") {
            showReminderDialog = true
        }

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        SectionTitle("Sleep Schedule")

        SettingItem(title = "Sleep Start Time", subtitle = "${sleepStart.toString().padStart(2, '0')}:00") {
            showSleepStartDialog = true
        }

        SettingItem(title = "Sleep End Time", subtitle = "${sleepEnd.toString().padStart(2, '0')}:00") {
            showSleepEndDialog = true
        }

        // Dialogs
        if (showReminderDialog) {
            SelectionDialog(
                title = "Select Reminder Frequency",
                options = listOf(15, 30, 45, 60, 90, 120, 180),
                selectedValue = reminderFrequency,
                onDismiss = { showReminderDialog = false },
                onConfirm = {
                    reminderFrequency = it
                    prefs.saveReminderFrequency(it)
                    NotificationScheduler.scheduleNotifications(context)
                }
            )
        }

        if (showSleepStartDialog) {
            TimePickerDialog(
                initialHour = sleepStart,
                initialMinute = 0,
                onDismissRequest = { showSleepStartDialog = false },
                onConfirm = { hour, _ ->
                    sleepStart = hour
                    prefs.saveSleepSchedule(sleepStart, sleepEnd)
                }
            )
        }

        if (showSleepEndDialog) {
            TimePickerDialog(
                initialHour = sleepEnd,
                initialMinute = 0,
                onDismissRequest = { showSleepEndDialog = false },
                onConfirm = { hour, _ ->
                    sleepEnd = hour
                    prefs.saveSleepSchedule(sleepStart, sleepEnd)
                }
            )
        }
        if (showThemeDialog) {
            AlertDialog(
                onDismissRequest = { showThemeDialog = false },
                title = { Text("Select Theme") },
                text = {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        themes.forEach { theme ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedTheme = theme
                                        onThemeChange(theme) // Trigger theme update
                                        showThemeDialog = false
                                    }
                                    .padding(vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = (theme.name == selectedTheme.name),
                                    onClick = {
                                        selectedTheme = theme
                                        onThemeChange(theme) // Trigger theme update
                                        showThemeDialog = false
                                    }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = theme.name)
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showThemeDialog = false }) {
                        Text("OK")
                    }
                }
            )
        }
    }
}
@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.padding(vertical = 12.dp)
    )
}

@Composable
fun SettingItem(title: String, subtitle: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp)
    ) {
        Text(text = title, style = MaterialTheme.typography.bodyLarge, color = Color.White, fontSize = 18.sp)
        Text(text = subtitle, style = MaterialTheme.typography.bodySmall, color = Color.Gray,
            fontSize = 15.sp)
    }
}

@Composable
fun SelectionDialog(
    title: String,
    options: List<Int>,
    selectedValue: Int,
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit
) {
    var selectedOption by remember { mutableIntStateOf(selectedValue) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onConfirm(selectedOption)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        title = { Text(title) },
        text = {
            Column {
                options.forEach { option ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedOption = option }
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (option == selectedOption),
                            onClick = { selectedOption = option }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "$option min")
                    }
                }
            }
        }
    )
}