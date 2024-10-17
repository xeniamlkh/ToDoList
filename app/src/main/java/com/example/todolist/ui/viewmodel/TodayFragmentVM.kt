package com.example.todolist.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.repository.ToDoListRepository
import com.example.todolist.data.repository.WeatherCacheRepository
import com.example.todolist.data.room.entity.ToDoListEntity
import com.example.todolist.data.room.entity.WeatherEntity
import dagger.internal.Provider
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

class TodayFragmentVMFactory @Inject constructor(private val provider: Provider<TodayFragmentVM>) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(TodayFragmentVM::class.java)) {
            return provider.get() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}