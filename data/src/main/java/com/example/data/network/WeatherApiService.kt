package com.example.data.network
//
//import retrofit2.http.GET
//import retrofit2.http.Query
//
//interface WeatherApiService {
//    @GET("weather")
//    suspend fun getCurrentWeather(
//        @Query("lat") lat: String,
//        @Query("lon") lon: String,
//        @Query("appid") apiKey: String,
//        @Query("units") units: String
//    ): WeatherData
//}