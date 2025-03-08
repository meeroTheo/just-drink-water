package com.jdw.justdrink.data

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class IntakeViewModel(private val repository: WaterIntakeRepository) : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    fun addWaterIntake(amount: Int) { //basic event handler to add the water intake and date to database
        viewModelScope.launch {
            val date = LocalDateTime.now()
            repository.insertOrUpdateIntake(WaterIntake(date = date, totalIntake = amount))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getTodayIntake(): Int? {
        val today = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
        return repository.getIntakeForDate(today)?.totalIntake
    }
}
