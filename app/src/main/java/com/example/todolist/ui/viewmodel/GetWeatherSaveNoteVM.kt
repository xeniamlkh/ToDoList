package com.example.todolist.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todolist.BuildConfig
import com.example.todolist.data.network.WeatherData
import com.example.todolist.data.repository.ToDoListRepository
import com.example.todolist.data.repository.WeatherRepository
import com.example.todolist.data.room.ToDoListEntity
import kotlinx.coroutines.launch

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
            _weatherData.value = weatherRepository.getCurrentWeather(lat, lon, apiKey)
        }
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