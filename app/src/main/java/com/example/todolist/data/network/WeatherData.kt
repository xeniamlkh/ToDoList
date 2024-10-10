package com.example.todolist.data.network

data class WeatherData(val main: Main, val name: String, var weather: ArrayList<Weather>) {
    data class Main(val temp: Float)
    data class Weather(val main: String)
}
