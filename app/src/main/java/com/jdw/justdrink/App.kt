package com.jdw.justdrink

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jdw.justdrink.components.BottomNavigationBar
import com.jdw.justdrink.components.home.HomePage
import com.jdw.justdrink.components.SettingsPage
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.jdw.justdrink.data.IntakeViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun App(intakeViewModel: IntakeViewModel) {
    val navController = rememberNavController()

    MaterialTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = { BottomNavigationBar(navController) }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") { HomePage(intakeViewModel) }
                    composable("settings") { SettingsPage() }
                }
            }
        }
    }
}
