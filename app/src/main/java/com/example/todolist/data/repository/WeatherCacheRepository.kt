package com.example.todolist.data.repository

import com.example.todolist.data.room.WeatherDao
import com.example.todolist.data.room.WeatherEntity
import kotlinx.coroutines.flow.Flow

class WeatherCacheRepository(private val weatherDao: WeatherDao) {

    suspend fun writeWeatherDataCache(weatherEntity: WeatherEntity) {
        weatherDao.writeWeatherDataCache(weatherEntity)
    }

    fun getWeatherDataCache(): Flow<WeatherEntity> {
        return weatherDao.getWeatherDataCache()
    }

    suspend fun updateWeatherDataCacheById(
        lat: String,
        lon: String,
        weatherEntityId: Int,
        cityName: String,
        weather: String,
        temperature: Int
    ) {
        weatherDao.updateWeatherDataCacheById(
            lat,
            lon,
            weatherEntityId,
            cityName,
            weather,
            temperature
        )
    }
}