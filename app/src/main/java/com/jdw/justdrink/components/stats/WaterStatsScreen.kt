package com.jdw.justdrink.components.stats

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jdw.justdrink.data.IntakeViewModel
import com.jdw.justdrink.data.StatsTimePeriod
import com.jdw.justdrink.data.WaterStatsData
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun WaterStatsScreen(
    viewModel: IntakeViewModel
) {
    val selectedPeriod by viewModel.selectedTimePeriod.collectAsStateWithLifecycle()
    val statsData by viewModel.statsData.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        StatsTimePeriodDropdown(
            selectedPeriod = selectedPeriod,
            onPeriodSelected = { viewModel.selectTimePeriod(it) }
        )

        Spacer(modifier = Modifier.height(24.dp))

        StatsDisplayGrid(statsData = statsData) //get data from ViewModel
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsTimePeriodDropdown(
    selectedPeriod: StatsTimePeriod,
    onPeriodSelected: (StatsTimePeriod) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val items = StatsTimePeriod.entries.toTypedArray()

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryNotEditable, enabled = true)
                .fillMaxWidth(),
            readOnly = true,
            value = selectedPeriod.displayName,
            onValueChange = {},
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { period ->
                DropdownMenuItem(
                    text = { Text(period.displayName) },
                    onClick = {
                        onPeriodSelected(period)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}

@Composable
fun StatsDisplayGrid(statsData: WaterStatsData) { //data Class from viewModel
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            StatDisplayBox(
                valueText = if (statsData.isLoading) "..." else formatTotalConsumption(statsData.totalConsumption),
                labelText = "Total Consumed"
            )
        }
        item {
            StatDisplayBox(
                valueText = if (statsData.isLoading) "..." else "${statsData.currentStreak}",
                labelText = "Current Streak (days)"
            )
        }
        item {
            StatDisplayBox(
                valueText = if (statsData.isLoading) "..." else "${statsData.averagePercentage.roundToInt()}%",
                labelText = "Avg. Goal %"
            )
        }
        item {
            StatDisplayBox(valueText = "", labelText = "")
        }
    }
}

// Helper function (same as before)
fun formatTotalConsumption(value: Int): String {
    return if (value >= 1000) {
        String.format(Locale.getDefault(),"%.1f L", value / 1000.0)
    } else {
        "$value ml"
    }
}

@Composable
fun StatDisplayBox(
    valueText: String,
    labelText: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.aspectRatio(1.1f),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF333333)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (valueText.isNotBlank()) {
                Text(
                    text = valueText,
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            if (labelText.isNotBlank()) {
                Text(
                    text = labelText,
                    color = Color.White,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 18.sp
                )
            }
        }
    }
}