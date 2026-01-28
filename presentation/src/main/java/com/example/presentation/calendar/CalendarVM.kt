package com.example.presentation.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CalendarVM: ViewModel() {

    private val _calendarDate = MutableLiveData<String>()
    val calendarDate: LiveData<String> get() = _calendarDate

    fun setCalendarDate(calendarDate: String) {
        _calendarDate.value = calendarDate
    }
}