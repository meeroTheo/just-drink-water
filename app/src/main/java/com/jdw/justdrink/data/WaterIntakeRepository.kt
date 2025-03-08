package com.jdw.justdrink.data

import java.time.LocalDateTime
import kotlinx.coroutines.flow.Flow

class WaterIntakeRepository(private val waterIntakeDao: WaterIntakeDao) {

    suspend fun getIntakeForDate(date: LocalDateTime): WaterIntake? {
        return waterIntakeDao.getIntakeForDate(date)
    }


    suspend fun insertOrUpdateIntake(waterIntake: WaterIntake) {
        val existingIntake = getIntakeForDate(waterIntake.date)
        if (existingIntake == null) {
            waterIntakeDao.insertIntake(waterIntake)
        } else {
            val updatedIntake = existingIntake.copy(totalIntake = existingIntake.totalIntake + waterIntake.totalIntake)
            waterIntakeDao.updateIntake(updatedIntake)
        }
    }
}
