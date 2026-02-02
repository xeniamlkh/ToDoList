package com.example.data.network

import com.example.domain.interfaces.WeatherService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class WeatherServiceModule {

    @Binds
    abstract fun bindWeatherService(impl: WeatherServiceImpl): WeatherService
}