package com.example.todolist.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todolist.BuildConfig
import com.example.todolist.data.repository.WeatherCacheRepository
import com.example.todolist.data.repository.WeatherNetworkRepository
import com.example.todolist.data.room.entity.WeatherEntity
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject
import dagger.internal.Provider
import kotlin.math.roundToInt

class MainActivityVM @Inject constructor(
    private val weatherCacheRepository: WeatherCacheRepository,
    private val weatherNetworkRepository: WeatherNetworkRepository
) : BaseViewModel() {

    private val apiKey = BuildConfig.API_KEY

    fun getCurrentWeather(lat: String, lon: String) {
        viewModelScope.launch {
            _weatherData.value = weatherNetworkRepository.getCurrentWeather(lat, lon, apiKey)

            val cityName = weatherData.value?.name
            val weatherConditions = weatherData.value?.weather?.get(0)?.main
            val temperature = weatherData.value?.main?.temp?.roundToInt()

            writeOrUpdateWeatherDataCache(lat, lon, cityName, weatherConditions, temperature)
        }
    }

    private fun writeOrUpdateWeatherDataCache(
        lat: String,
        lon: String,
        cityName: String?,
        weatherConditions: String?,
        temperature: Int?
    ) {
        if (cityName != null && weatherConditions != null && temperature != null) {
            val weatherEntity = WeatherEntity(
                0,
                lat,
                lon,
                cityName,
                weatherConditions,
                temperature
            )


            viewModelScope.launch {
                val weatherDataCache = weatherCacheRepository.getWeatherDataCache().firstOrNull()

                if (weatherDataCache == null) {
                    writeWeatherDataCache(weatherEntity)
                } else {
                    updateWeatherDataCacheById(
                        1,
                        lat,
                        lon,
                        weatherEntity.cityName,
                        weatherEntity.weatherConditions,
                        weatherEntity.temperature
                    )
                }
            }
        }
    }

    private suspend fun writeWeatherDataCache(weatherEntity: WeatherEntity) {
        weatherCacheRepository.writeWeatherDataCache(weatherEntity)
    }

    private suspend fun updateWeatherDataCacheById(
        weatherEntityId: Int,
        lat: String,
        lon: String,
        cityName: String,
        weather: String,
        temperature: Int
    ) {
        weatherCacheRepository.updateWeatherDataCacheById(
            lat,
            lon,
            weatherEntityId,
            cityName,
            weather,
            temperature
        )
    }
}

class MainActivityVMFactory @Inject constructor(private val provider: Provider<MainActivityVM>) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(MainActivityVM::class.java)) {
            return provider.get() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}