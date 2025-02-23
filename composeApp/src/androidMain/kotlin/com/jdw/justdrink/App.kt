package com.jdw.justdrink

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

@Composable
fun App() {
    val navController = rememberNavController()

    MaterialTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = { BottomNavigationBar(navController) }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") { HomePage() }
                    composable("settings") { SettingsPage() }
                }
            }
        }
    }
}

@Preview
@Composable
fun AppPreview() {
    App()
}