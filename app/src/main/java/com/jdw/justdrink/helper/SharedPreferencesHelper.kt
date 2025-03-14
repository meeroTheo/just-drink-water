package com.jdw.justdrink.helper

import android.content.Context
import androidx.core.content.edit

class SharedPreferencesHelper(context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences("WaterIntakePrefs", Context.MODE_PRIVATE)

    fun saveCustomSize(size: Int) {
        sharedPreferences.edit {
            putInt("customSize", size)
        }
    }

    fun getCustomSize(): Int? {
        return if (sharedPreferences.contains("customSize")) {
            sharedPreferences.getInt("customSize", 0)
        } else {
            null
        }
    }

    // Save notification frequency in minutes
    fun saveReminderFrequency(frequency: Int) {
        sharedPreferences.edit {
            putInt("reminderFrequency", frequency)
        }
    }

    fun getReminderFrequency(): Int {
        return sharedPreferences.getInt("reminderFrequency", 60) // Default to 60 mins
    }

    // Save user's sleep schedule (start and end in 24-hour format)
    fun saveSleepSchedule(sleepStart: Int, sleepEnd: Int) {
        sharedPreferences.edit {
            putInt("sleepStart", sleepStart)
            putInt("sleepEnd", sleepEnd)
        }
    }

    fun getSleepStart(): Int {
        return sharedPreferences.getInt("sleepStart", 23) // Default to 11 PM
    }

    fun getSleepEnd(): Int {
        return sharedPreferences.getInt("sleepEnd", 7) // Default to 7 AM
    }
}
