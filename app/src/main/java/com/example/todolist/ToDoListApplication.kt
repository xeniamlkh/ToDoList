package com.example.todolist

import android.app.Application
import android.content.Context
import androidx.core.content.ContextCompat
//import com.example.todolist.data.network.WeatherApiClient
import com.example.todolist.data.network.WeatherApiService
import com.example.todolist.data.repository.ToDoListRepository
import com.example.todolist.data.repository.WeatherCacheRepository
import com.example.todolist.data.repository.WeatherNetworkRepository
import com.example.todolist.data.room.dao.ToDoListDao
import com.example.todolist.data.room.dao.WeatherDao
import com.example.todolist.data.room.database.ToDoListDatabase
//import com.example.todolist.di.component.AppComponent
//import com.example.todolist.di.component.DaggerAppComponent
//import com.example.todolist.di.module.service.NetworkServiceModule
//import com.example.todolist.di.module.service.RoomServiceModule
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

    // private lateinit var appComponent: AppComponent


    override fun onCreate() {
        super.onCreate()
//        appComponent = DaggerAppComponent
//            .builder()
//            .roomServiceModule(RoomServiceModule(this))
//            .networkServiceModule(NetworkServiceModule())
//            .build()
    }

//    fun getAppComponent(): AppComponent {
//        return appComponent
//    }
}

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

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

//    @Provides
//    @Singleton
//    fun getNetworkRepository(weatherApiService: WeatherApiService): WeatherNetworkRepository {
//        return WeatherNetworkRepository(weatherApiService)
//    }

//    @Provides
//    @Singleton
//    fun getCacheRepository(weatherDao: WeatherDao): WeatherCacheRepository{
//        return WeatherCacheRepository(weatherDao)
//    }

//    @Provides
//    @Singleton
//    fun getRoomRepository(notesDao: ToDoListDao): ToDoListRepository{
//        return ToDoListRepository(notesDao)
//    }

//    @Singleton
//    @Provides
//    fun getWeatherApiService(): WeatherApiService {
//        return WeatherApiClient.retrofit.create(WeatherApiService::class.java)
//    }

//    @Singleton
//    @Provides
//    fun getDatabase(): ToDoListDatabase {
//        return ToDoListDatabase.getDatabase(provideAppContext())
//    }

//    @Singleton
//    @Provides
//    fun provideAppContext(): Context {
//        return application.applicationContext
//    }

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
}