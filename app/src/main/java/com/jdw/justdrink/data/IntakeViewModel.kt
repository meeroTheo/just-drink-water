package com.jdw.justdrink.data

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class IntakeViewModel(private val repository: WaterIntakeRepository) : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    fun addWaterIntake(amount: Int) {
        viewModelScope.launch {
            val date = LocalDateTime.now()
            repository.insertOrUpdateIntake(WaterIntake(date = date, totalIntake = amount))
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getTodayIntake(): Int? {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return null
        }

        val today = LocalDateTime.now()
        return repository.getIntakeForDate(today)?.totalIntake ?: 0
    }
}
