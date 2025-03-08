package com.jdw.justdrink


import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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
