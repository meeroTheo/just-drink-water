package com.jdw.justdrink

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import com.jdw.justdrink.components.HomeScreen
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
fun App() {
    MaterialTheme {
        HomeScreen()
    }
}