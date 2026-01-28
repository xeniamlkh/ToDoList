package com.example.todolist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.data.network.WeatherData
import com.example.data.repository.WeatherCacheRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

//TODO Check weather methods
@HiltViewModel
class MainActivityVM @Inject constructor(
    private val weatherCacheRepository: WeatherCacheRepository,
    //private val weatherNetworkRepository: WeatherNetworkRepository
) : ViewModel() {

    private var _weatherData = MutableLiveData<WeatherData>()
    val weatherData: LiveData<WeatherData> get() = _weatherData

    private val _quoteStatus = MutableLiveData<Boolean>()
    val quoteStatus: LiveData<Boolean> get() = _quoteStatus

    fun showQuote(value: Boolean) {
        _quoteStatus.value = value
    }

    //TODO Temporary
    //private val apiKey = BuildConfig.API_KEY

    //TODO Temporary
//    fun getCurrentWeather(lat: String, lon: String) {
//        viewModelScope.launch {
//            _weatherData.value = weatherNetworkRepository.getCurrentWeather(lat, lon, apiKey)
//
//            val cityName = weatherData.value?.name
//            val weatherConditions = weatherData.value?.weather?.get(0)?.main
//            val temperature = weatherData.value?.main?.temp?.roundToInt()
//
//            writeOrUpdateWeatherDataCache(lat, lon, cityName, weatherConditions, temperature)
//        }
//    }

    //TODO Temporary
//    private fun writeOrUpdateWeatherDataCache(
//        lat: String,
//        lon: String,
//        cityName: String?,
//        weatherConditions: String?,
//        temperature: Int?
//    ) {
//        if (cityName != null && weatherConditions != null && temperature != null) {
//            val weatherEntity = WeatherEntity(
//                0,
//                lat,
//                lon,
//                cityName,
//                weatherConditions,
//                temperature
//            )
//
//
//            viewModelScope.launch {
//                val weatherDataCache = weatherCacheRepository.getWeatherDataCache().firstOrNull()
//
//                if (weatherDataCache == null) {
//                    writeWeatherDataCache(weatherEntity)
//                } else {
//                    updateWeatherDataCacheById(
//                        1,
//                        lat,
//                        lon,
//                        weatherEntity.cityName,
//                        weatherEntity.weatherConditions,
//                        weatherEntity.temperature
//                    )
//                }
//            }
//        }
//    }

    //TODO Temporary
//    private suspend fun writeWeatherDataCache(weatherEntity: WeatherEntity) {
//        weatherCacheRepository.writeWeatherDataCache(weatherEntity)
//    }

    //TODO Temporary
//    private suspend fun updateWeatherDataCacheById(
//        weatherEntityId: Int,
//        lat: String,
//        lon: String,
//        cityName: String,
//        weather: String,
//        temperature: Int
//    ) {
//        weatherCacheRepository.updateWeatherDataCacheById(
//            lat,
//            lon,
//            weatherEntityId,
//            cityName,
//            weather,
//            temperature
//        )
//    }
}