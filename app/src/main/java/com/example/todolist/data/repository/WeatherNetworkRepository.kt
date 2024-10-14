package com.example.todolist.data.repository

import com.example.todolist.data.network.WeatherApiClient
import com.example.todolist.data.network.WeatherApiService
import com.example.todolist.data.network.WeatherData

class WeatherNetworkRepository {

    private val weatherApiService: WeatherApiService =
        WeatherApiClient.retrofit.create(WeatherApiService::class.java)

    suspend fun getCurrentWeather(lat: String, lon: String, apiKey: String): WeatherData? {
        return try {
            weatherApiService.getCurrentWeather(lat, lon, apiKey, "metric")
        } catch (e: Exception) {
            null
        }
    }
}