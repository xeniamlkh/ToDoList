package com.example.todolist.di.module.service

import android.content.Context
import com.example.todolist.ToDoListApplication
import com.example.todolist.data.room.dao.ToDoListDao
import com.example.todolist.data.room.database.ToDoListDatabase
import com.example.todolist.data.room.dao.WeatherDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

//@Module
//class RoomServiceModule(val application: ToDoListApplication) {
//
//    @Singleton
//    @Provides
//    fun getDatabase(): ToDoListDatabase {
//        return ToDoListDatabase.getDatabase(provideAppContext())
//    }
//
//    @Singleton
//    @Provides
//    fun provideAppContext(): Context {
//        return application.applicationContext
//    }
//
//    @Singleton
//    @Provides
//    fun getToDoListDao(database: ToDoListDatabase): ToDoListDao {
//        return database.notesDao()
//    }
//
//    @Singleton
//    @Provides
//    fun getWeatherDao(database: ToDoListDatabase): WeatherDao {
//        return database.weatherDao()
//    }
//}