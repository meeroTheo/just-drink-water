package com.jdw.justdrink.components.stats

import androidx.compose.runtime.Composable

import com.jdw.justdrink.data.IntakeViewModel

@Composable
fun StatsPage(viewModel: IntakeViewModel) {


    WaterIntakeChart(viewModel)

}