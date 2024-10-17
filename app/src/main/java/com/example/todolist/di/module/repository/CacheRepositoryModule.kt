package com.example.todolist.di.module.repository

import com.example.todolist.data.repository.WeatherCacheRepository
import com.example.todolist.data.room.dao.WeatherDao
import com.example.todolist.di.component.annotation.ActivityScope
import dagger.Module
import dagger.Provides

@Module
class CacheRepositoryModule {

    @ActivityScope
    @Provides
    fun getCacheRepository(weatherDao: WeatherDao): WeatherCacheRepository{
        return WeatherCacheRepository(weatherDao)
    }
}