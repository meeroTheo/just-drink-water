package com.jdw.justdrink.components.home

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import com.jdw.justdrink.helper.SharedPreferencesHelper

@Composable
fun WaterIntakeButtons(
    context: Context,
    onBottleSelected: (Int) -> Unit,
    customSize: Int?
) {
    val sharedPreferencesHelper = remember { SharedPreferencesHelper(context) }
    var localCustomSize by remember { mutableStateOf(customSize ?: sharedPreferencesHelper.getCustomSize()) }
    var showDialog by remember { mutableStateOf(false) }
    var inputValue by remember { mutableStateOf(TextFieldValue("")) }

    val bottleSizes = listOf(50, 100, 150, 200, 250) + (localCustomSize?.let { listOf(it) } ?: listOf("Custom"))

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerLow)
            .border(
                width = 1.5.dp,
                color = MaterialTheme.colorScheme.secondary,
                shape = RoundedCornerShape(25.dp)
            )

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
                modifier = Modifier
                    .size(90.dp).border(
                        width = 0.2.dp,
                        color = MaterialTheme.colorScheme.secondary,

                    ),

                shape = RectangleShape,
                containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.LocalCafe,
                        contentDescription = "Cup Icon",
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = if (size == "Custom") "Custom" else "$size ml",
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Enter Custom Size") },
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            text = {
                OutlinedTextField(
                    value = inputValue,
                    onValueChange = { inputValue = it },
                    label = {
                        Text(
                            text = "Size",
                            style = TextStyle(color = MaterialTheme.colorScheme.outline) // Change label color here
                        )
                    }


                )
            }
            ,
            confirmButton = {
                Button(onClick = {
                    inputValue.text.toIntOrNull()?.let { size ->
                        localCustomSize = size
                        sharedPreferencesHelper.saveCustomSize(size)
                        onBottleSelected(size)
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
