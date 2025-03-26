package com.jdw.justdrink.helper

import android.content.Context
import androidx.core.content.edit
import com.jdw.justdrink.ui.theme.AppTheme

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

    //notification frequency
    fun saveReminderFrequency(frequency: Int) {
        sharedPreferences.edit {
            putInt("reminderFrequency", frequency)
        }
    }

    fun getReminderFrequency(): Int {
        return sharedPreferences.getInt("reminderFrequency", 60)
    }

    fun saveSleepSchedule(sleepStart: Int, sleepEnd: Int) {
        sharedPreferences.edit {
            putInt("sleepStart", sleepStart)
            putInt("sleepEnd", sleepEnd)
        }
    }

    fun getSleepStart(): Int {
        return sharedPreferences.getInt("sleepStart", 23)
    }

    fun getSleepEnd(): Int {
        return sharedPreferences.getInt("sleepEnd", 7)
    }

    fun isFirstLaunch(): Boolean {
        return sharedPreferences.getBoolean("firstLaunch", true)
    }

    fun setFirstLaunchDone() {
        sharedPreferences.edit {
            putBoolean("firstLaunch", false)
        }
    }
    fun saveUserName(name: String) {
        sharedPreferences.edit {
            putString("userName", name)
        }
    }

    fun getUserName(): String? {
        return sharedPreferences.getString("userName", "")
    }


    fun saveSelectedTheme(themeName: String) {
        sharedPreferences.edit().putString("selected_theme", themeName).apply()
    }

    fun getSelectedTheme(): String {
        return sharedPreferences.getString("selected_theme", AppTheme.Default.name) ?: AppTheme.Default.name
    }

}
