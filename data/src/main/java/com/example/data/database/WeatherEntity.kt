package com.example.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_data")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo val lat: String,
    @ColumnInfo val lon: String,
    @ColumnInfo(name = "name") val cityName: String,
    @ColumnInfo(name = "weather conditions") val weatherConditions: String,
    @ColumnInfo(name = "temperature") val temperature: Int
)