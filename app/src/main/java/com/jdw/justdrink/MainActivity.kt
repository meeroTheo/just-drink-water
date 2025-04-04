package com.jdw.justdrink

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.jdw.justdrink.components.firstlaunch.FirstTimeLaunchScreen
import com.jdw.justdrink.data.WaterDatabase
import com.jdw.justdrink.data.WaterIntakeRepository
import com.jdw.justdrink.data.IntakeViewModel
import com.jdw.justdrink.helper.SharedPreferencesHelper
import com.jdw.justdrink.ui.theme.AppTheme
import com.jdw.justdrink.ui.theme.themes

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //ask for notification permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1001)
            }
        }
        //Initialize Database
        val database = WaterDatabase.getDatabase(applicationContext)
        val repository = WaterIntakeRepository(database.waterIntakeDao())
        //Initialize ViewModel
        val intakeViewModel = IntakeViewModel(repository)




        setContent {
            val prefs = SharedPreferencesHelper(this)
            var selectedTheme by remember { mutableStateOf(themes.find { it.name == prefs.getSelectedTheme() } ?: AppTheme.Default) }

            AppTheme(
                darkTheme = isSystemInDarkTheme(),
                dynamicColor = false,
                selectedTheme = selectedTheme
            ) {
                var isFirstLaunch by remember { mutableStateOf(prefs.isFirstLaunch()) }

                if (isFirstLaunch) {
                    FirstTimeLaunchScreen(
                        onSetupComplete = { isFirstLaunch = false }, context = this)
                } else {
                    App(
                        intakeViewModel = intakeViewModel,
                        onThemeChange = { newTheme ->
                            selectedTheme = newTheme
                            prefs.saveSelectedTheme(newTheme.name) // Persist selection
                        }
                    )
                }
            }
        }
    }
}