package com.example.data.repositories

//import com.example.data.database.WeatherDao
//import com.example.data.database.WeatherEntity
//import kotlinx.coroutines.flow.Flow
//import javax.inject.Inject
//
//class WeatherCacheRepository @Inject constructor(private val weatherDao: WeatherDao) {
//
//    suspend fun writeWeatherDataCache(weatherEntity: WeatherEntity) {
//        weatherDao.writeWeatherDataCache(weatherEntity)
//    }
//
//    fun getWeatherDataCache(): Flow<WeatherEntity> {
//        return weatherDao.getWeatherDataCache()
//    }
//
//    suspend fun updateWeatherDataCacheById(
//        lat: String,
//        lon: String,
//        weatherEntityId: Int,
//        cityName: String,
//        weather: String,
//        temperature: Int
//    ) {
//        weatherDao.updateWeatherDataCacheById(
//            lat,
//            lon,
//            weatherEntityId,
//            cityName,
//            weather,
//            temperature
//        )
//    }
//}