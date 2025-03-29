package com.jdw.justdrink.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface WaterIntakeDao {
    @Query("SELECT * FROM water_intake WHERE strftime('%Y-%m-%d', date) = :date LIMIT 1")
    suspend fun getIntakeForDate(date: String): WaterIntake?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIntake(waterIntake: WaterIntake)

    @Update
    suspend fun updateIntake(waterIntake: WaterIntake)

    @Query("SELECT * FROM water_intake ORDER BY date ASC")
    suspend fun getAllIntake(): List<WaterIntake>


    /**
     * Gets all intake records from a specific start date/time onwards.
     * Note: Assumes date is stored as ISO string, allowing direct comparison.
     */
    @Query("SELECT * FROM water_intake WHERE date >= :startDateTimeString ORDER BY date ASC")
    suspend fun getIntakeSince(startDateTimeString: String): List<WaterIntake>

    /**
     * Gets all intake records sorted by date descending (newest first).
     * Useful for streak calculation.
     */
    @Query("SELECT * FROM water_intake ORDER BY date DESC")
    suspend fun getAllIntakeSortedDesc(): List<WaterIntake>
}