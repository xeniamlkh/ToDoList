package com.example.todolist.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.repository.ToDoListRepository
import com.example.todolist.data.repository.WeatherCacheRepository
import com.example.todolist.data.room.entity.ToDoListEntity
import com.example.todolist.data.room.entity.WeatherEntity
import kotlinx.coroutines.launch
import javax.inject.Inject

class TodayFragmentVM @Inject constructor(
    private val toDoListRepository: ToDoListRepository,
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