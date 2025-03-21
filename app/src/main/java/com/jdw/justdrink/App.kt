package com.jdw.justdrink


import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jdw.justdrink.components.BottomNavigationBar
import com.jdw.justdrink.components.home.HomePage
import com.jdw.justdrink.components.settings.SettingsPage
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.jdw.justdrink.data.IntakeViewModel
import androidx.compose.ui.platform.LocalContext


@Composable
fun App(intakeViewModel: IntakeViewModel) {
    val navController = rememberNavController()
    val context = LocalContext.current // Get the context

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = { BottomNavigationBar(navController) }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(MaterialTheme.colorScheme.surfaceContainerLowest)
            ) {
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") { HomePage(intakeViewModel) }
                    composable("settings") { SettingsPage(context) }
                }
            }
        }
    }

}
