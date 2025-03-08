package com.jdw.justdrink.helper

import android.content.Context
import androidx.core.content.edit

class SharedPreferencesHelper(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("WaterIntakePrefs", Context.MODE_PRIVATE)

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
}