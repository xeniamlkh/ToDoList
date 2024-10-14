package com.example.todolist

import android.app.Application
import com.example.todolist.data.room.ToDoListDatabase
import com.example.todolist.data.repository.ToDoListRepository
import com.example.todolist.data.repository.WeatherCacheRepository

class ToDoListApplication : Application() {
    val database: ToDoListDatabase by lazy { ToDoListDatabase.getDatabase(this) }

    val toDoListRepository: ToDoListRepository by lazy {
        ToDoListRepository(database.notesDao())
    }
    val weatherCacheRepository: WeatherCacheRepository by lazy {
        WeatherCacheRepository(database.weatherDao())
    }
}