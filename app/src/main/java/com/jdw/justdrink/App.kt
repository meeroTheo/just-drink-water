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
import com.jdw.justdrink.components.stats.StatsPage
import com.jdw.justdrink.ui.theme.AppTheme


@Composable
fun App(
    intakeViewModel: IntakeViewModel,
    onThemeChange: (AppTheme) -> Unit
) {
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
                    composable("home") { HomePage(intakeViewModel, LocalContext.current) }
                    composable("stats") { StatsPage(intakeViewModel) }
                    composable("settings") { SettingsPage(context, onThemeChange = onThemeChange) }
                }
            }
        }
    }

}
