package com.jdw.justdrink.data


import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.time.LocalDateTime

@Entity(tableName = "water_intake")
data class WaterIntake (
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val date: LocalDateTime,
    val totalIntake: Int
)

class LocalDateTimeConverter {

    @TypeConverter
    fun timeToString(time: LocalDateTime): String{
        return time.toString()
    }

    @TypeConverter
    fun stringToTime(string: String): LocalDateTime{
        return LocalDateTime.parse(string)
    }
}