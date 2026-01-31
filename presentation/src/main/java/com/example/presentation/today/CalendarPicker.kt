package com.example.presentation.today

import android.app.DatePickerDialog
import android.app.Dialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.presentation.R
import java.text.SimpleDateFormat
import java.util.Locale

class CalendarPicker : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private val viewModel: TodayFragmentVM by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(
            requireContext(), R.style.CustomDatePickerDialog,
            this, year, month, day
        )
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

        val date = Calendar.getInstance()
        date.set(year, month, dayOfMonth)

        val simpleCalendarDate = SimpleDateFormat("d MMM yyyy, EEEE", Locale.getDefault())
        val calendarDate: String = simpleCalendarDate.format(date.time)

        viewModel.setCalendarDate(calendarDate)
    }
}