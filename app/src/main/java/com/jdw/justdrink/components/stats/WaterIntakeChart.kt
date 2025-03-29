package com.jdw.justdrink.components.stats

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.jdw.justdrink.data.IntakeViewModel
import kotlinx.coroutines.launch
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import kotlin.math.max
import kotlin.math.min

@Composable
fun WaterIntakeChart(viewModel: IntakeViewModel) {
    val chartData = remember { mutableStateOf(emptyList<Pair<String, Float>>()) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val themeColors = MaterialTheme.colorScheme

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            chartData.value = viewModel.getDailyIntakePercentages()
        }
    }

    AndroidView(
        factory = {
            LineChart(it).apply {

                setupChartStyle(context, this, chartData.value, themeColors)
            }
        },
        update = { chart ->
            setupChartStyle(context, chart, chartData.value, themeColors)

            //tell chart the data has changed
            chart.notifyDataSetChanged()
            //redraw of the chart view
            chart.invalidate()

        },
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(themeColors.surfaceContainerLow, RoundedCornerShape(12.dp))
    )
}

fun setupChartStyle(context: Context, chart: LineChart, chartData: List<Pair<String, Float>>, themeColors: androidx.compose.material3.ColorScheme) {
    if (chartData.isEmpty()) {
        chart.clear()
        chart.setNoDataText("No data available.")
        chart.setNoDataTextColor(themeColors.onSurfaceVariant.toArgb())
        chart.invalidate()
        return
    }

    if (chart.isEmpty) {
        chart.setNoDataText("")
    }

    val entries = chartData.mapIndexed { index, data ->
        Entry(index.toFloat(), minOf(data.second, 100f))
    }

    val line_Color = themeColors.secondary.toArgb()
    val text_Color = themeColors.outlineVariant.toArgb()
    val grid_Color = themeColors.outlineVariant.copy(alpha = 0.5f).toArgb()

    val fill_Drawable = createGradientDrawable(
        resources = context.resources,
        startColor = themeColors.secondary.copy(alpha = 0.5f).toArgb(),
        endColor = themeColors.surfaceContainer.copy(alpha = 0.1f).toArgb()
    )

    val dataSet = LineDataSet(entries, "").apply {
        color = line_Color
        valueTextColor = text_Color
        lineWidth = 3f
        setDrawFilled(true)
        fillDrawable = fill_Drawable
        setDrawCircles(false)
        mode = LineDataSet.Mode.CUBIC_BEZIER
        setDrawValues(false)
        setDrawHorizontalHighlightIndicator(false)
        setDrawVerticalHighlightIndicator(true)
        highLightColor = themeColors.tertiary.toArgb()
        highlightLineWidth = 1.5f
    }

    chart.apply {
        data = LineData(dataSet)
        legend.isEnabled = false
        setDrawGridBackground(false)
        description.isEnabled = false
        setTouchEnabled(true)
        setPinchZoom(false)
        isDragEnabled = true
        setScaleEnabled(false)

        extraLeftOffset = 25f
        extraRightOffset = 30f
        extraTopOffset = 25f
        extraBottomOffset = 25f

        xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            textSize = 14f
            textColor = Color.Gray.toArgb()
            granularity = 1f
            isGranularityEnabled = true
            valueFormatter = IndexAxisValueFormatter(chartData.map { it.first })

            val desiredLabelCount = if (chartData.isNotEmpty()) {
                min(chartData.size, 5)
            } else {
                0
            }
            val finalLabelCount = if (chartData.size > 1) max(2, desiredLabelCount) else desiredLabelCount

            if (finalLabelCount > 0) {
                setLabelCount(finalLabelCount, true)
            }
            setCenterAxisLabels(false)
            setAvoidFirstLastClipping(false)

            setDrawGridLines(true)
            gridColor = grid_Color
            gridLineWidth = 0.6f
            enableGridDashedLine(6f, 6f, 0f)
            setDrawAxisLine(false)
        }

        axisLeft.apply {
            axisMaximum = 105f
            axisMinimum = 1f
            textSize = 15f
            textColor = text_Color
            setDrawGridLines(true)
            gridColor = grid_Color
            gridLineWidth = 0.6f
            enableGridDashedLine(6f, 6f, 0f)
            setDrawAxisLine(false)
            valueFormatter = com.github.mikephil.charting.formatter.DefaultAxisValueFormatter(0)
        }

        axisRight.isEnabled = false

    }
}

fun createGradientDrawable(resources: Resources, startColor: Int, endColor: Int, width: Int = 100, height: Int = 100): Drawable {
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    val paint = Paint().apply {
        shader = LinearGradient(
            0f, 0f, 0f, height.toFloat(),
            startColor,
            endColor,
            Shader.TileMode.CLAMP
        )
    }
    canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
    return BitmapDrawable(resources, bitmap)
}
