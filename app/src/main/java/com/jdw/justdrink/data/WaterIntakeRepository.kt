package com.jdw.justdrink.data

import java.time.LocalDateTime
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
        return data
    }

    /**
     * Fetches water intake records from a given start date/time up to the present.
     */
    suspend fun getIntakeSince(startDate: LocalDateTime): List<WaterIntake> {
        val startDateTimeString = startDate.toString()
        return waterIntakeDao.getIntakeSince(startDateTimeString)
    }

    /**
     * Fetches all water intake records sorted from newest to oldest date.
     */
    suspend fun getAllIntakeSortedDesc(): List<WaterIntake> {
        return waterIntakeDao.getAllIntakeSortedDesc()
    }
}