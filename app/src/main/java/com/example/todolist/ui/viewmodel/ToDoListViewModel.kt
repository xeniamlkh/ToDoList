package com.example.todolist.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.todolist.BuildConfig
import com.example.todolist.model.data.ToDoListEntity
import com.example.todolist.model.network.WeatherData
import com.example.todolist.model.repository.ToDoListRepository
import com.example.todolist.model.repository.WeatherRepository
import kotlinx.coroutines.launch

class ToDoListViewModel(private val repository: ToDoListRepository) : ViewModel() {

    // ROOM
    private val _calendarDate = MutableLiveData<String>()
    val calendarDate: LiveData<String> get() = _calendarDate

    fun getListOfNotesByDate(date: String): LiveData<List<ToDoListEntity>> {
        return repository.getListOfNotesByDate(date).asLiveData()
    }

    fun setCalendarDate(calendarDate: String) {
        _calendarDate.value = calendarDate
    }

    private fun insertNote(note: ToDoListEntity) {
        viewModelScope.launch {
            repository.insertNote(note)
        }
    }

    fun createNote(date: String, checkboxStatus: Boolean, note: String) {
        val noteRecord = ToDoListEntity(0, date, checkboxStatus, note)
        insertNote(noteRecord)
    }

    fun deleteNoteById(noteId: Int) {
        viewModelScope.launch {
            repository.deleteNoteById(noteId)
        }
    }

    fun updateNoteById(noteId: Int, noteText: String) {
        viewModelScope.launch {
            repository.updateNoteById(noteId, noteText)
        }
    }

    fun updateNoteStatus(noteId: Int, checkBoxStatus: Boolean) {
        viewModelScope.launch {
            repository.updateNoteStatus(noteId, checkBoxStatus)
        }
    }

    fun showAllFinishedTasks(): LiveData<List<ToDoListEntity>> {
        return repository.showAllFinishedTasks().asLiveData()
    }

    fun showAllUnfinishedTasks(): LiveData<List<ToDoListEntity>> {
        return repository.showAllUnfinishedTasks().asLiveData()
    }

    fun deleteAllFinishedTasks() {
        viewModelScope.launch { repository.deleteAllFinishedTasks() }
    }

    // WEATHER
    private val weatherRepository = WeatherRepository()
    private val apiKey = BuildConfig.API_KEY

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
}

class ToDoListViewModelFactory(private val repository: ToDoListRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(ToDoListViewModel::class.java)) {
            return ToDoListViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}