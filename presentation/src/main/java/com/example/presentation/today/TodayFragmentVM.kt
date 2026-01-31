package com.example.presentation.today

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.interfaces.NotesRepository
import com.example.domain.models.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TAG = "!!!!!"
//rename to SharedViewModel
//private val weatherCacheRepository: WeatherCacheRepository
//    private var _weatherData = MutableLiveData<WeatherData>()
//    val weatherData: LiveData<WeatherData> get() = _weatherData
@HiltViewModel
class TodayFragmentVM @Inject constructor(
    private val repository: NotesRepository,
) : ViewModel() {

    private val _calendarDate = MutableLiveData<String>()
    val calendarDate: LiveData<String> get() = _calendarDate

    fun setCalendarDate(calendarDate: String) {
        _calendarDate.value = calendarDate
    }

    fun createNote(date: String, checkboxStatus: Boolean, text: String) {
        val note = Note(0, date = date, checkboxStatus = checkboxStatus, text = text)
        viewModelScope.launch {
            repository.insertNote(note)
        }
    }


    //TODO Temporary
//    fun getWeatherDataCache(): LiveData<WeatherEntity> {
//        return weatherCacheRepository.getWeatherDataCache().asLiveData()
//    }

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