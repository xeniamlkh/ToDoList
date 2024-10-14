package com.example.todolist.data.repository

import com.example.todolist.data.room.ToDoListDao
import com.example.todolist.data.room.ToDoListEntity
import com.example.todolist.data.room.WeatherDao
import com.example.todolist.data.room.WeatherEntity
import kotlinx.coroutines.flow.Flow

//TODO separate (?) this into two repositories: one for notes, another for weather cache
class ToDoListRepository(private val notesDao: ToDoListDao, private val weatherDao: WeatherDao) {

    suspend fun insertNote(note: ToDoListEntity) {
        notesDao.insertNote(note)
    }

    suspend fun deleteNoteById(noteId: Int) {
        notesDao.deleteNoteById(noteId)
    }

    fun getListOfNotesByDate(date: String): Flow<List<ToDoListEntity>> {
        return notesDao.getListOfNotesByDate(date)
    }

    suspend fun updateNoteById(noteId: Int, noteText: String) {
        notesDao.updateNoteById(noteId, noteText)
    }

    suspend fun updateNoteStatus(noteId: Int, checkBoxStatus: Boolean) {
        notesDao.updateNoteStatus(noteId, checkBoxStatus)
    }

    fun showAllFinishedTasks(): Flow<List<ToDoListEntity>> {
        return notesDao.showAllFinishedTasks()
    }

    fun showAllUnfinishedTasks(): Flow<List<ToDoListEntity>> {
        return notesDao.showAllUnfinishedTasks()
    }

    suspend fun deleteAllFinishedTasks() {
        notesDao.deleteAllFinishedTasks()
    }

    suspend fun writeWeatherDataCache(weatherEntity: WeatherEntity) {
        weatherDao.writeWeatherDataCache(weatherEntity)
    }

    suspend fun deleteWeatherDataCache(weatherEntityId: Int) {
        weatherDao.deleteWeatherDataCache(weatherEntityId)
    }

    fun getWeatherDataCache(): Flow<WeatherEntity> {
        return weatherDao.getWeatherDataCache()
    }

    suspend fun updateWeatherDataCacheById(
        lat: String,
        lon: String,
        weatherEntityId: Int,
        cityName: String,
        weather: String,
        temperature: Int
    ) {
        weatherDao.updateWeatherDataCacheById(
            lat,
            lon,
            weatherEntityId,
            cityName,
            weather,
            temperature
        )
    }
}