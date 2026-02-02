package com.example.data.repositories

import com.example.data.network.WeatherApi
import com.example.domain.interfaces.WeatherService
import com.example.domain.models.WeatherData
import javax.inject.Inject

class WeatherServiceImpl @Inject constructor(private val weatherApi: WeatherApi): WeatherService {

//    @Inject
//    lateinit var weatherApi: WeatherApi

    override suspend fun getCurrentWeather(
        lat: String,
        lon: String,
        apiKey: String,
        units: String
    ): WeatherData {
        return weatherApi.getCurrentWeather(
            lat = lat,
            lon = lon,
            apiKey = apiKey,
            units = units
        )
    }
}