package com.example.presentation.today

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.interfaces.NotesRepository
import com.example.domain.models.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

//rename to SharedViewModel
@HiltViewModel
class TodayFragmentVM @Inject constructor(
    //private val toDoListRepository: NotesRepository,
    //private val weatherCacheRepository: WeatherCacheRepository
) : ViewModel() {

    private val _calendarDate = MutableLiveData<String>()
    val calendarDate: LiveData<String> get() = _calendarDate

    private val _quoteStatus = MutableLiveData<Boolean>()
    val quoteStatus: LiveData<Boolean> get() = _quoteStatus

//    private var _weatherData = MutableLiveData<WeatherData>()
//    val weatherData: LiveData<WeatherData> get() = _weatherData

    fun showQuote(value: Boolean) {
        _quoteStatus.value = value
    }

    fun setCalendarDate(calendarDate: String) {
        _calendarDate.value = calendarDate
    }

    fun createNote(date: String, checkboxStatus: Boolean, text: String) {
        val note = Note(0, date = date, checkboxStatus = checkboxStatus, text = text)
        viewModelScope.launch {
            //toDoListRepository.insertNote(note)
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