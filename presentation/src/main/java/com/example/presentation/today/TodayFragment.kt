package com.example.presentation.today

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.domain.interfaces.WeatherService
import com.example.domain.models.WeatherData
import com.example.presentation.BaseFragment
import com.example.presentation.R
import com.example.presentation.databinding.FragmentTodayBinding
import com.example.presentation.noteslist.NotesListFragment
import com.example.presentation.util.getTodayDate
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "!!!!!"

@AndroidEntryPoint
class TodayFragment : BaseFragment<FragmentTodayBinding>() {

    private var finishedTasks: Boolean = false
    private var unfinishedTasks: Boolean = false
    private var actualDate: String? = null

    private val viewModel: TodayFragmentVM by activityViewModels()

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        lifecycleScope.launch {
            val coarseLocationPermission = result
                .getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false)

            val fineLocationPermission = result
                .getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false)

            if ((coarseLocationPermission) && (fineLocationPermission)) {
                viewModel.getActualForecast()
            } else {
                viewModel.setQuoteStatus(true)
            }
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTodayBinding {
        return FragmentTodayBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            checkLocationPermissions()

            val todayDate = requireContext().getTodayDate()
            binding.todayDate.text = todayDate

            updateNotesList(todayDate, byDateCond = true, finishedCond = false)
        } else {
            val savedDate = savedInstanceState.getString("currentDate")
            actualDate = savedDate
            binding.todayDate.text = savedDate

            finishedTasks = savedInstanceState.getBoolean("finishedTasks")
            unfinishedTasks = savedInstanceState.getBoolean("unfinishedTasks")
        }

        viewModel.permissionConfirmationStatus.observe(this.viewLifecycleOwner) { permissionStatus ->
            if (permissionStatus) {
                permissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                )
            }
        }

        viewModel.locationError.observe(this.viewLifecycleOwner) { locationError ->
            if (locationError && savedInstanceState == null) {
                showSnackBar(R.string.gps_location_error)
            }
        }

        viewModel.networkError.observe(this.viewLifecycleOwner) { networkError ->
            if (networkError && savedInstanceState == null) {
                showSnackBar(R.string.network_error)
            }
        }

        viewModel.quoteStatus.observe(this.viewLifecycleOwner) { quoteStatus ->
            if (quoteStatus) {
                binding.apply {
                    loadingProgressBar.visibility = View.GONE
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
                    loadingProgressBar.visibility = View.GONE
                    weatherIcon.visibility = View.VISIBLE
                    cityName.visibility = View.VISIBLE
                    temperature.visibility = View.VISIBLE
                    condition.visibility = View.VISIBLE
                    quote.visibility = View.GONE
                    goodDayIcon.visibility = View.GONE
                }
            }
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

        viewModel.weatherConditions.observe(this.viewLifecycleOwner) { weatherConditions ->
            if (weatherConditions.isNotEmpty()) {
                binding.condition.text = weatherConditions
            } else {
                binding.condition.text = ""
            }

            //TODO add icons + add empty icon for no condition (else branch)
            when (weatherConditions) {
                "Thunderstorm" -> binding.weatherIcon.setImageResource(R.drawable.ic_11d)
                "Drizzle" -> binding.weatherIcon.setImageResource(R.drawable.ic_09d)
                "Rain" -> binding.weatherIcon.setImageResource(R.drawable.ic_10d)
                "Snow" -> binding.weatherIcon.setImageResource(R.drawable.ic_13d)
                "Clear" -> binding.weatherIcon.setImageResource(R.drawable.ic_01d)
                "Clouds" -> binding.weatherIcon.setImageResource(R.drawable.ic_03d)
                else -> binding.weatherIcon.setImageResource(R.drawable.ic_50d)
            }
        }

        viewModel.city.observe(this.viewLifecycleOwner) { city ->
            if (city.isNotEmpty()) {
                binding.cityName.text = city
            } else {
                binding.cityName.text = ""
            }
        }

        viewModel.temperature.observe(this.viewLifecycleOwner) { temperature ->
            val stringBuilder = StringBuilder()
            stringBuilder.append(temperature.toString()).append(" \u2103")
            binding.temperature.text = stringBuilder.toString()
        }


        binding.todayDate.setOnClickListener {
            finishedTasks = false
            unfinishedTasks = false
            binding.warningNoteRequired.visibility = View.GONE
            CalendarPicker().show(getParentFragmentManager(), "CALENDAR_PICKER")
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
    }

    private fun updateNotesList(date: String, byDateCond: Boolean, finishedCond: Boolean) {
        getChildFragmentManager().beginTransaction()
            .replace(
                R.id.recycler_view_fragment_container,
                NotesListFragment.newInstance(date, byDateCond, finishedCond)
            )
            .commit()
    }

    private fun checkLocationPermissions() {
        Log.d(TAG, "checkLocationPermissions: ...")
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                viewModel.getActualForecast()
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) -> {
                showRationalDialog()
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
                showRationalDialog()
            }

            else -> {
                permissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                )
            }
        }
    }

    private fun showRationalDialog() {
        PermissionRationaleDialog().show(parentFragmentManager, "RATIONALE")
    }

    private fun showSnackBar(stringId: Int) {
        Snackbar.make(
            requireActivity().findViewById(android.R.id.content),
            getString(stringId), Snackbar.LENGTH_SHORT
        )
            .show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("currentDate", binding.todayDate.text.toString())
        outState.putBoolean("finishedTasks", finishedTasks)
        outState.putBoolean("unfinishedTasks", unfinishedTasks)
    }
}


//    private fun getCachedWeather(weatherCache: WeatherEntity) {
//        if (weatherCache.cityName.isEmpty()) {
//            binding.apply {
//                loadingProgressBar.visibility = View.GONE
//                goodDayIcon.setImageResource(R.drawable.ic_nice_day)
//                goodDayIcon.visibility = View.VISIBLE
//                weatherIcon.visibility = View.GONE
//                cityName.visibility = View.GONE
//                temperature.visibility = View.GONE
//                condition.visibility = View.GONE
//                quote.text = Fragment.getResources.getString(R.string.have_a_nice_day)
//                quote.visibility = View.VISIBLE
//            }
//        } else {
//            binding.apply {
//                loadingProgressBar.visibility = View.GONE
//                goodDayIcon.visibility = View.GONE
//                weatherIcon.visibility = View.VISIBLE
//                cityName.visibility = View.VISIBLE
//                temperature.visibility = View.VISIBLE
//                condition.visibility = View.VISIBLE
//                quote.visibility = View.GONE
//
//            }
//
//            val cityName = weatherCache.cityName
//            val weatherConditions = weatherCache.weatherConditions
//            val temperature = weatherCache.temperature
//            val stringBuilder = StringBuilder()
//            stringBuilder.append(temperature.toString()).append(" \u2103")
//
//            binding.cityName.text = cityName
//            binding.condition.text = weatherConditions
//            binding.temperature.text = stringBuilder.toString()
//
//            when (weatherConditions) {
//                "Thunderstorm" -> binding.weatherIcon.setImageResource(R.drawable.ic_11d)
//                "Drizzle" -> binding.weatherIcon.setImageResource(R.drawable.ic_09d)
//                "Rain" -> binding.weatherIcon.setImageResource(R.drawable.ic_10d)
//                "Snow" -> binding.weatherIcon.setImageResource(R.drawable.ic_13d)
//                "Clear" -> binding.weatherIcon.setImageResource(R.drawable.ic_01d)
//                "Clouds" -> binding.weatherIcon.setImageResource(R.drawable.ic_03d)
//                else -> binding.weatherIcon.setImageResource(R.drawable.ic_50d)
//            }
//        }
//    }