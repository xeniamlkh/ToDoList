package com.example.todolist.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.todolist.R
import com.example.todolist.ToDoListApplication
import com.example.todolist.databinding.FragmentTodayBinding
import com.example.todolist.ui.utils.getTodayDate
import com.example.todolist.ui.viewmodel.ToDoListViewModel
import com.example.todolist.ui.viewmodel.ToDoListViewModelFactory
import kotlin.math.roundToInt

class TodayFragment : BaseFragment<FragmentTodayBinding>() {

    private var finishedTasks: Boolean = false
    private var unfinishedTasks: Boolean = false
    private var actualDate: String? = null

    private val viewModel: ToDoListViewModel by activityViewModels {
        ToDoListViewModelFactory(
            (activity?.application as ToDoListApplication).repository
        )
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTodayBinding {
        return FragmentTodayBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState != null) {
            val savedDate = savedInstanceState.getString("currentDate")
            actualDate = savedDate
            binding.todayDate.text = savedDate

            finishedTasks = savedInstanceState.getBoolean("finishedTasks")
            unfinishedTasks = savedInstanceState.getBoolean("unfinishedTasks")

        } else {
            val todayDate = requireContext().getTodayDate()

            binding.todayDate.text = todayDate
            updateNotesList(todayDate, byDateCond = true, finishedCond = false)
        }

        viewModel.calendarDate.observe(this.viewLifecycleOwner) { calendarDate ->
            binding.todayDate.text = calendarDate

            if (savedInstanceState != null) {
                if (!actualDate.equals(calendarDate)) {
                    updateNotesList(calendarDate, byDateCond = true, finishedCond = false)
                }
            } else {
                if (!finishedTasks && !unfinishedTasks) {
                    updateNotesList(calendarDate, byDateCond = true, finishedCond = false)
                }
            }
        }

        binding.todayDate.setOnClickListener {
            finishedTasks = false
            unfinishedTasks = false
            binding.warningNoteRequired.visibility = View.GONE
            CalendarPickerFragment().show(parentFragmentManager, "CALENDAR_PICKER")
        }

        binding.saveNoteBtn.setOnClickListener {
            finishedTasks = false
            unfinishedTasks = false
            if (binding.inputText.text.isNullOrEmpty()) {
                binding.warningNoteRequired.visibility = View.VISIBLE
            } else {
                binding.warningNoteRequired.visibility = View.GONE
                viewModel.createNote(
                    binding.todayDate.text.toString(), false,
                    binding.inputText.text.toString()
                )
                updateNotesList(
                    binding.todayDate.text.toString(),
                    byDateCond = true,
                    finishedCond = false
                )
            }
            binding.inputText.text?.clear()
        }

        binding.showFinishedBtn.setOnClickListener {
            finishedTasks = true
            unfinishedTasks = false
            binding.warningNoteRequired.visibility = View.GONE
            updateNotesList("00 May 0000, Sunday", byDateCond = false, finishedCond = true)
        }

        binding.showUnfinishedBtn.setOnClickListener {
            unfinishedTasks = true
            finishedTasks = false
            binding.warningNoteRequired.visibility = View.GONE
            updateNotesList(date = "00 May 0000, Sunday", byDateCond = false, finishedCond = false)
        }

        viewModel.weatherData.observe(this.viewLifecycleOwner) { weatherData ->
            if (weatherData != null && weatherData.name.isNotEmpty()) {
                binding.loadingProgressBar.visibility = View.GONE
                val weatherConditions = weatherData.weather[0].main
                when (weatherConditions) {
                    "Thunderstorm" -> binding.weatherIcon.setImageResource(R.drawable.ic_11d)
                    "Drizzle" -> binding.weatherIcon.setImageResource(R.drawable.ic_09d)
                    "Rain" -> binding.weatherIcon.setImageResource(R.drawable.ic_10d)
                    "Snow" -> binding.weatherIcon.setImageResource(R.drawable.ic_13d)
                    "Clear" -> binding.weatherIcon.setImageResource(R.drawable.ic_01d)
                    "Clouds" -> binding.weatherIcon.setImageResource(R.drawable.ic_03d)
                    else -> binding.weatherIcon.setImageResource(R.drawable.ic_50d)
                }

                binding.cityName.text = weatherData.name
                val temperature = weatherData.main.temp.roundToInt()

                val stringBuilder = StringBuilder()
                stringBuilder.append(temperature.toString()).append(" \u2103")
                binding.temperature.text = stringBuilder.toString()

                binding.condition.text = weatherData.weather[0].main
            }
        }

        viewModel.quoteStatus.observe(this.viewLifecycleOwner) { quoteStatus ->
            if (quoteStatus) {
                binding.apply {
                    binding.loadingProgressBar.visibility = View.GONE
                    goodDayIcon.setImageResource(R.drawable.ic_nice_day)
                    goodDayIcon.visibility = View.VISIBLE
                    weatherIcon.visibility = View.GONE
                    cityName.visibility = View.GONE
                    temperature.visibility = View.GONE
                    condition.visibility = View.GONE
                    quote.text = resources.getString(R.string.have_a_nice_day)
                    quote.visibility = View.VISIBLE
                }
            } else {
                binding.apply {
                    weatherIcon.visibility = View.VISIBLE
                    cityName.visibility = View.VISIBLE
                    temperature.visibility = View.VISIBLE
                    condition.visibility = View.VISIBLE
                    quote.visibility = View.GONE
                    goodDayIcon.visibility = View.GONE
                }
            }
        }
    }

    private fun updateNotesList(date: String, byDateCond: Boolean, finishedCond: Boolean) {
        childFragmentManager.beginTransaction()
            .replace(
                R.id.recycler_view_fragment_container,
                NotesListFragment.newInstance(date, byDateCond, finishedCond)
            )
            .commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("currentDate", binding.todayDate.text.toString())
        outState.putBoolean("finishedTasks", finishedTasks)
        outState.putBoolean("unfinishedTasks", unfinishedTasks)
    }
}