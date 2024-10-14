package com.example.todolist.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.todolist.BuildConfig
import com.example.todolist.data.network.WeatherData
import com.example.todolist.data.repository.WeatherCacheRepository
import com.example.todolist.data.repository.WeatherNetworkRepository
import com.example.todolist.data.room.WeatherEntity
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class GetWeatherVM(private val weatherCacheRepository: WeatherCacheRepository) : ViewModel() {

    private val weatherNetworkRepository = WeatherNetworkRepository()
    private val apiKey = BuildConfig.API_KEY

    private val _quoteStatus = MutableLiveData<Boolean>()
    val quoteStatus: LiveData<Boolean> get() = _quoteStatus

    private val _weatherData = MutableLiveData<WeatherData>()
    val weatherData: LiveData<WeatherData> get() = _weatherData

    fun showQuote(value: Boolean) {
        _quoteStatus.value = value
    }

    fun getCurrentWeather(lat: String, lon: String) {
        viewModelScope.launch {
            _weatherData.value = weatherNetworkRepository.getCurrentWeather(lat, lon, apiKey)

            val cityName = weatherData.value?.name
            val weatherConditions = weatherData.value?.weather?.get(0)?.main
            val temperature = weatherData.value?.main?.temp?.roundToInt()

            writeOrUpdateWeatherDataCache(lat, lon, cityName, weatherConditions, temperature)
        }
    }

    fun getWeatherDataCache(): LiveData<WeatherEntity> {
        return weatherCacheRepository.getWeatherDataCache().asLiveData()
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

class GetWeatherVMFactory(private val weatherCacheRepository: WeatherCacheRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(GetWeatherVM::class.java)) {
            return GetWeatherVM(weatherCacheRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}