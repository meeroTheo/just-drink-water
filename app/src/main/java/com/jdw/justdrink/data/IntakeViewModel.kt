package com.jdw.justdrink.data


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class IntakeViewModel(private val repository: WaterIntakeRepository) : ViewModel() {


    fun addWaterIntake(amount: Int) { //basic event handler to add the water intake and date to database
        viewModelScope.launch {
            val date = LocalDateTime.now()
            repository.insertOrUpdateIntake(WaterIntake(date = date, totalIntake = amount))
        }
    }

    suspend fun getTodayIntake(): Int? {
        val today = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
        return repository.getIntakeForDate(today)?.totalIntake
    }

    suspend fun getDailyIntakePercentages(): List<Pair<String, Float>> {
        val intakeData = repository.getAllIntake()
            .sortedBy { it.date }
            .takeLast(5) //keep only the last 5 days

        return intakeData.map {
            val formattedDate = it.date.format(DateTimeFormatter.ofPattern("MMM d"))
            val percentage = (it.totalIntake / 3000f) * 100
            formattedDate to percentage
        }
    }

}
