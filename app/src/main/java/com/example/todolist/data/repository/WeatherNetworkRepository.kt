package com.example.todolist.data.repository

import com.example.todolist.data.network.WeatherApiService
import com.example.todolist.data.network.WeatherData
import javax.inject.Inject

class WeatherNetworkRepository @Inject constructor(private val weatherApiService: WeatherApiService) {

    suspend fun getCurrentWeather(lat: String, lon: String, apiKey: String): WeatherData? {
        return try {
            weatherApiService.getCurrentWeather(lat, lon, apiKey, "metric")
        } catch (e: Exception) {
            null
        }
    }
}