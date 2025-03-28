package com.jdw.justdrink.components
import androidx.navigation.NavController
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavController) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("Home","Stats","More")

    val selectedIcons = listOf(Icons.Filled.WaterDrop,Icons.Filled.BarChart, Icons.Filled.MoreHoriz)
    val unselectedIcons = listOf(Icons.Outlined.WaterDrop,Icons.Outlined.BarChart, Icons.Outlined.MoreHoriz)

    //get the current destination
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.surfaceContainer

    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = if (selectedItem == index) selectedIcons[index] else unselectedIcons[index],
                        contentDescription = item
                    )
                },
                label = { Text(
                    text = item,
                    fontSize = 13.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    when (index) {
                        0 -> {
                            if (currentDestination != "home") {
                                navController.navigate("home")
                            }
                        }
                        1 -> {
                            if (currentDestination != "stats") {
                                navController.navigate("stats")
                            }
                        }
                        2 -> {
                            if (currentDestination != "settings") {
                                navController.navigate("settings")
                            }
                        }
                    }
                }
            )
        }
    }
}
