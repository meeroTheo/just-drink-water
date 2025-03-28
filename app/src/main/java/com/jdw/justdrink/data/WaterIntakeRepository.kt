package com.jdw.justdrink.data

import java.time.format.DateTimeFormatter


class WaterIntakeRepository(private val waterIntakeDao: WaterIntakeDao) {

    suspend fun getIntakeForDate(date: String): WaterIntake? {
        return waterIntakeDao.getIntakeForDate(date)
    }


    suspend fun insertOrUpdateIntake(waterIntake: WaterIntake) {
        val today = waterIntake.date.format(DateTimeFormatter.ISO_LOCAL_DATE)
        val existingIntake = getIntakeForDate(today)

        if (existingIntake == null) {
            waterIntakeDao.insertIntake(waterIntake)
        } else {
            val updatedIntake = existingIntake.copy(totalIntake = existingIntake.totalIntake + waterIntake.totalIntake)
            waterIntakeDao.updateIntake(updatedIntake)
        }
    }

    suspend fun getAllIntake(): List<WaterIntake> {
        val data = waterIntakeDao.getAllIntake()
        println("Database returned: $data") // Debugging output
        return data
    }
}
