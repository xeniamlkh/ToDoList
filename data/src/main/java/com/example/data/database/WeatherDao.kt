package com.example.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun writeWeatherDataCache(weatherEntity: WeatherEntity)

    @Query("SELECT * FROM weather_data")
    fun getWeatherDataCache(): Flow<WeatherEntity>

    @Query("UPDATE weather_data SET id = :weatherEntityId, lat = :lat, lon = :lon, name = :cityName, `weather conditions`= :weather, temperature = :temperature WHERE id = :weatherEntityId")
    suspend fun updateWeatherDataCacheById(
        lat: String,
        lon: String,
        weatherEntityId: Int,
        cityName: String,
        weather: String,
        temperature: Int
    )
}