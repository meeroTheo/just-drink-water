package com.jdw.justdrink

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.work.Configuration
import androidx.work.WorkManager
import com.jdw.justdrink.data.WaterDatabase
import com.jdw.justdrink.data.WaterIntakeRepository
import com.jdw.justdrink.data.IntakeViewModel
import com.jdw.justdrink.ui.theme.AppTheme

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
            AppTheme {
                App(intakeViewModel) // Pass ViewModel to App()
            }
        }
    }
}
