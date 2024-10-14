package com.example.todolist.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DateVM : ViewModel() {

    private val _calendarDate = MutableLiveData<String>()
    val calendarDate: LiveData<String> get() = _calendarDate

    fun setCalendarDate(calendarDate: String) {
        _calendarDate.value = calendarDate
    }
}