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
}
