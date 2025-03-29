package com.jdw.justdrink.data // Or your ViewModel package


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

//enum class can be refactored to different file later
enum class StatsTimePeriod(val displayName: String, val days: Long?) {
    DAYS_7("7 days", 7L),
    DAYS_14("14 days", 14L),
    DAYS_30("30 days", 30L),
    DAYS_90("90 days", 90L),
    DAYS_180("180 days", 180L),
    DAYS_365("365 days", 365L),
    ALL_TIME("All time", null)
}

data class WaterStatsData(
    val totalConsumption: Int = 0,
    val currentStreak: Int = 0,
    val averagePercentage: Float = 0f,
    val isLoading: Boolean = true
)

//DAILY GOAL NEEDS TO BE CHANGED TO REFLECT USERS PREFERENCE
const val DAILY_WATER_GOAL_ML = 3000


class IntakeViewModel(private val repository: WaterIntakeRepository) : ViewModel() {

    fun addWaterIntake(amount: Int) { //basic event handler to add the water intake and date to database
        viewModelScope.launch {
            val date = LocalDateTime.now()
            repository.insertOrUpdateIntake(WaterIntake(date = date, totalIntake = amount))
            // After adding data, refresh the stats for the current period
            calculateStats(_selectedTimePeriod.value)
        }
    }

    suspend fun getTodayIntake(): Int? {
        val today = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE) // Use LocalDate format
        return repository.getIntakeForDate(today)?.totalIntake
    }

    suspend fun getDailyIntakePercentages(): List<Pair<String, Float>> {
        val intakeData = repository.getAllIntake()
            .sortedBy { it.date }
            .takeLast(5) //keep only the last 5 days

        return intakeData.map {
            val formattedDate = it.date.format(DateTimeFormatter.ofPattern("MMM d"))
            // Use the constant goal
            val percentage = (it.totalIntake / DAILY_WATER_GOAL_ML.toFloat()) * 100
            formattedDate to percentage.coerceAtMost(100f) // Cap at 100%?
        }
    }

    private val _selectedTimePeriod = MutableStateFlow(StatsTimePeriod.DAYS_30)
    val selectedTimePeriod: StateFlow<StatsTimePeriod> = _selectedTimePeriod.asStateFlow()

    private val _statsData = MutableStateFlow(WaterStatsData(isLoading = true))
    val statsData: StateFlow<WaterStatsData> = _statsData.asStateFlow()

    //stats calculation
    init {
        calculateStats(_selectedTimePeriod.value)
    }

    fun selectTimePeriod(period: StatsTimePeriod) {
        if (period == _selectedTimePeriod.value && !_statsData.value.isLoading) return

        _selectedTimePeriod.value = period
        calculateStats(period) //recalculate when period changes
    }

    private fun calculateStats(period: StatsTimePeriod) {
        viewModelScope.launch {
            _statsData.value = WaterStatsData(isLoading = true)

            val now = LocalDateTime.now()
            val startDate = period.days?.let {
                now.minusDays(it).truncatedTo(ChronoUnit.DAYS) //start of the day 'it' days ago
            }

            //fetch data
            //use IO dispatcher for database access
            val relevantIntake = withContext(Dispatchers.IO) {
                if (startDate != null) {
                    repository.getIntakeSince(startDate)
                } else {
                    repository.getAllIntake() //get all data for ALL_TIME (already sorted ASC)
                }
            }
            //for streak all data sorted DESC
            val allIntakeDesc = withContext(Dispatchers.IO) {
                repository.getAllIntakeSortedDesc()
            }

            withContext(Dispatchers.Default) {
                val total = calculateTotalConsumption(relevantIntake)
                val average = calculateAveragePercentage(relevantIntake, DAILY_WATER_GOAL_ML)
                val streak = calculateCurrentStreak(allIntakeDesc, DAILY_WATER_GOAL_ML)

                _statsData.value = WaterStatsData(
                    totalConsumption = total,
                    currentStreak = streak,
                    averagePercentage = average,
                    isLoading = false
                )
            }
        }
    }

    private fun calculateTotalConsumption(data: List<WaterIntake>): Int {
        return data.sumOf { it.totalIntake }
    }

    private fun calculateAveragePercentage(data: List<WaterIntake>, dailyGoal: Int): Float {
        if (data.isEmpty() || dailyGoal <= 0) return 0f

        val dailyTotals = data.groupBy { it.date.toLocalDate() }
            .mapValues { (_, entries) -> entries.sumOf { it.totalIntake } }

        if (dailyTotals.isEmpty()) return 0f

        val totalPercentageSum = dailyTotals.values.map { total -> (total.toFloat() / dailyGoal.toFloat()) * 100f }.sum()

        //average over the number of unique days with data within the period
        return totalPercentageSum / dailyTotals.size
    }

    private fun calculateCurrentStreak(allIntakeSortedDesc: List<WaterIntake>, dailyGoal: Int): Int {
        var currentStreak = 0
        val today = LocalDate.now()
        var expectedDate = today

        for (intake in allIntakeSortedDesc) {
            val intakeDate = intake.date.toLocalDate()
            if (intakeDate > expectedDate) continue //skip future entries

            if (intakeDate == expectedDate) {
                if (intake.totalIntake >= dailyGoal) {
                    currentStreak++
                    expectedDate = expectedDate.minusDays(1)
                } else {
                    if (expectedDate == today) currentStreak = 0 //reset if today goal wasn't met
                    break // Streak broken
                }
            } else if (intakeDate < expectedDate) {
                if (expectedDate == today) currentStreak = 0 //reset if no entry today
                break
            }
        }
        return currentStreak
    }

}