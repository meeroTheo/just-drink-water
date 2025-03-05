package com.jdw.justdrink.components.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.ui.text.input.TextFieldValue

@Composable
fun WaterIntakeButtons(onBottleSelected: (Int) -> Unit, customSize: Int?) {
    var localCustomSize by remember { mutableStateOf(customSize) }
    var showDialog by remember { mutableStateOf(false) }
    var inputValue by remember { mutableStateOf(TextFieldValue("")) }

    val bottleSizes = listOf(50, 100, 150, 200, 250) + (localCustomSize ?: "Custom")

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.padding(20.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(bottleSizes.size) { index ->
            val size = bottleSizes[index]

            FloatingActionButton(
                onClick = {
                    if (size == "Custom") {
                        showDialog = true
                    } else {
                        onBottleSelected(size as Int)
                    }
                },
                modifier = Modifier.size(65.dp),
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Filled.LocalCafe,
                        contentDescription = "Cup Icon",
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = if (size == "Custom") "Custom" else "$size ml",
                        fontSize = 10.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
    //when custom is clicked
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Enter Custom Size") },
            text = {
                OutlinedTextField(
                    value = inputValue,
                    onValueChange = { inputValue = it },
                    label = { Text("Size in ml") }
                )
            },
            confirmButton = {
                Button(onClick = {
                    inputValue.text.toIntOrNull()?.let {
                        localCustomSize = it
                        onBottleSelected(it)
                    }
                    showDialog = false
                }) {
                    Text("Set")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
