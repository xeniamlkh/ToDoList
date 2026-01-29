package com.example.presentation.util

import android.content.Context
import com.example.presentation.R
import java.text.SimpleDateFormat
import java.util.Locale

fun Context.getTodayDate(): String {
    val simpleTodayDate = SimpleDateFormat(getString(R.string.d_mmm_yyyy_eeee), Locale.getDefault())
    return simpleTodayDate.format(System.currentTimeMillis())
}