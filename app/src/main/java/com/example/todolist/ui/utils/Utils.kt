package com.example.todolist.ui.utils

import android.content.Context
import com.example.todolist.R
import java.text.SimpleDateFormat
import java.util.Locale

fun Context.getTodayDate(): String {
    val simpleTodayDate = SimpleDateFormat(getString(R.string.d_mmm_yyyy_eeee), Locale.getDefault())
    return simpleTodayDate.format(System.currentTimeMillis())
}