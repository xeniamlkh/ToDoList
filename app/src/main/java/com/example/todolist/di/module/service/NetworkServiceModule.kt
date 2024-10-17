package com.example.todolist.di.module.service

import com.example.todolist.data.network.WeatherApiClient
import com.example.todolist.data.network.WeatherApiService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkServiceModule {

    @Singleton
    @Provides
    fun getWeatherApiService(): WeatherApiService {
        return WeatherApiClient.retrofit.create(WeatherApiService::class.java)
    }
}