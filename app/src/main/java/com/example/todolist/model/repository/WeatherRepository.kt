package com.example.todolist.model.repository

import com.example.todolist.model.network.WeatherApiClient
import com.example.todolist.model.network.WeatherApiService
import com.example.todolist.model.network.WeatherData

class WeatherRepository {

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