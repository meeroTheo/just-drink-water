package com.jdw.justdrink.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import java.time.LocalDateTime

@Dao
interface WaterIntakeDao {
    @Query("SELECT * FROM water_intake WHERE date = :date LIMIT 1")
    suspend fun getIntakeForDate(date: LocalDateTime): WaterIntake?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIntake(waterIntake: WaterIntake)

    @Update
    suspend fun updateIntake(waterIntake: WaterIntake)
}