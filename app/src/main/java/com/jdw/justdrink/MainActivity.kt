package com.jdw.justdrink

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.jdw.justdrink.data.WaterDatabase
import com.jdw.justdrink.data.WaterIntakeRepository
import com.jdw.justdrink.data.IntakeViewModel
import com.jdw.justdrink.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
