package com.example.todolist

import android.app.Application
import com.example.todolist.data.network.WeatherApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@HiltAndroidApp
class ToDoListApplication : Application() {

    @Module
    @InstallIn(SingletonComponent::class)
    object NetworkModule {
        private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

        @Provides
        @Singleton
        fun providesRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        @Provides
        @Singleton
        fun provideWeatherApiService(retrofit: Retrofit): WeatherApiService {
            return retrofit.create(WeatherApiService::class.java)
        }
    }
}