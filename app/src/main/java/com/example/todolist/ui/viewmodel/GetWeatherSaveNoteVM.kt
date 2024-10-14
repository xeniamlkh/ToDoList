package com.example.todolist.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.todolist.BuildConfig
import com.example.todolist.data.network.WeatherData
import com.example.todolist.data.repository.ToDoListRepository
import com.example.todolist.data.repository.WeatherRepository
import com.example.todolist.data.room.ToDoListEntity
import com.example.todolist.data.room.WeatherEntity
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

private const val TAG = "!!!!!"

// + CalendarPickerFragment
// + MainActivity
class GetWeatherSaveNoteVM(private val repository: ToDoListRepository) : ViewModel() {

    private val weatherRepository = WeatherRepository()
    private val apiKey = BuildConfig.API_KEY

    private val _calendarDate = MutableLiveData<String>()
    val calendarDate: LiveData<String> get() = _calendarDate

    private val _weatherData = MutableLiveData<WeatherData>()
    val weatherData: LiveData<WeatherData> get() = _weatherData

    private val _quoteStatus = MutableLiveData<Boolean>()
    val quoteStatus: LiveData<Boolean> get() = _quoteStatus

    fun getCurrentWeather(lat: String, lon: String) {
        viewModelScope.launch {
            // 1/ Set weatherData value
            _weatherData.value = weatherRepository.getCurrentWeather(lat, lon, apiKey)

            val cityName = weatherData.value?.name
            val weatherConditions = weatherData.value?.weather?.get(0)?.main
            val temperature = weatherData.value?.main?.temp?.roundToInt()

//            val weatherEntity = WeatherEntity(
//                0,
//                cityName ?: "",
//                weatherConditions ?: "",
//                temperature ?: 0
//            )

            // 2 Update cache
            writeOrUpdateWeatherDataCache(lat, lon, cityName, weatherConditions, temperature)
        }
    }

    fun getWeatherDataCache(): LiveData<WeatherEntity> {
        return repository.getWeatherDataCache().asLiveData()
    }

    fun showQuote(value: Boolean) {
        _quoteStatus.value = value
    }

    fun setCalendarDate(calendarDate: String) {
        _calendarDate.value = calendarDate
    }

    fun createNote(date: String, checkboxStatus: Boolean, note: String) {
        val noteRecord = ToDoListEntity(0, date, checkboxStatus, note)
        insertNote(noteRecord)
    }

    private fun insertNote(note: ToDoListEntity) {
        viewModelScope.launch {
            repository.insertNote(note)
        }
    }

    // TODO pass properties as a parameter -> check if city = "" => don't update cache
    private fun writeOrUpdateWeatherDataCache(
        lat: String,
        lon: String,
        cityName: String?,
        weatherConditions: String?,
        temperature: Int?
    ) {
        Log.d(TAG, "writeOrUpdateWeatherDataCache: cityName = $cityName")
        Log.d(TAG, "writeOrUpdateWeatherDataCache: weatherConditions = $weatherConditions")
        Log.d(TAG, "writeOrUpdateWeatherDataCache: temperature = $temperature")

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
                val weatherDataCache = repository.getWeatherDataCache().firstOrNull()

                if (weatherDataCache == null) {
                    writeWeatherDataCache(weatherEntity)
                } else {
                    updateWeatherDataCacheById(
                        1, // or 0???
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
        repository.writeWeatherDataCache(weatherEntity)
    }

    private suspend fun updateWeatherDataCacheById(
        weatherEntityId: Int,
        lat: String,
        lon: String,
        cityName: String,
        weather: String,
        temperature: Int
    ) {
        repository.updateWeatherDataCacheById(
            lat,
            lon,
            weatherEntityId,
            cityName,
            weather,
            temperature
        )
    }

    suspend fun deleteWeatherDataCache(weatherEntityId: Int) {
        repository.deleteWeatherDataCache(weatherEntityId)
    }
}

class GetWeatherSaveNoteVMFactory(private val repository: ToDoListRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(GetWeatherSaveNoteVM::class.java)) {
            return GetWeatherSaveNoteVM(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}