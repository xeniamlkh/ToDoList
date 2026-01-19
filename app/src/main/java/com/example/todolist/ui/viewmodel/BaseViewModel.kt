package com.example.todolist.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolist.data.network.WeatherData
import dagger.hilt.android.lifecycle.HiltViewModel

open class BaseViewModel : ViewModel() {

    private val _calendarDate = MutableLiveData<String>()
    val calendarDate: LiveData<String> get() = _calendarDate

    fun setCalendarDate(calendarDate: String) {
        _calendarDate.value = calendarDate
    }

    val _weatherData = MutableLiveData<WeatherData>()
    val weatherData: LiveData<WeatherData> get() = _weatherData

    private val _quoteStatus = MutableLiveData<Boolean>()
    val quoteStatus: LiveData<Boolean> get() = _quoteStatus

    fun showQuote(value: Boolean) {
        _quoteStatus.value = value
    }

}