package com.jdw.justdrink.components.stats

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import com.jdw.justdrink.data.IntakeViewModel

@Composable
fun StatsPage(viewModel: IntakeViewModel) {

    Column(modifier = Modifier.fillMaxWidth()) {
        WaterStatsScreen(viewModel)
        WaterIntakeChart(viewModel)
    }


}