package com.example.presentation.today

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class TodayFragmentVM @Inject constructor(
    private val toDoListRepository: NotesRepositoryImpl,
    private val weatherCacheRepository: WeatherCacheRepository
) : BaseViewModel() {

    fun createNote(date: String, checkboxStatus: Boolean, note: String) {
        val noteRecord = ToDoListEntity(0, date, checkboxStatus, note)
        insertNote(noteRecord)
    }

    private fun insertNote(note: ToDoListEntity) {
        viewModelScope.launch {
            toDoListRepository.insertNote(note)
        }
    }

    fun getWeatherDataCache(): LiveData<WeatherEntity> {
        return weatherCacheRepository.getWeatherDataCache().asLiveData()
    }
}