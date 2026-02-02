package com.example.domain.interfaces

import com.example.domain.models.WeatherData

interface WeatherService {
    suspend fun getCurrentWeather(
        lat: String,
        lon: String,
        apiKey: String,
        units: String
    ): WeatherData?
}