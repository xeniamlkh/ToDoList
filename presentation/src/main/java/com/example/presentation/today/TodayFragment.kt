package com.example.presentation.today

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.domain.enums.SortCriteria
import com.example.presentation.BaseFragment
import com.example.presentation.R
import com.example.presentation.databinding.FragmentTodayBinding
import com.example.presentation.noteslist.NotesListFragment
import com.example.presentation.util.getTodayDate
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TodayFragment : BaseFragment<FragmentTodayBinding>() {

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

            viewModel.setSortingCriteria(SortCriteria.DATE)
        } else {
            val savedDate = savedInstanceState.getString("currentDate")
            actualDate = savedDate
            binding.todayDate.text = savedDate
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

        viewModel.weatherConditions.observe(this.viewLifecycleOwner) { weatherConditions ->
            if (weatherConditions.isNotEmpty()) {
                binding.condition.text = weatherConditions
            } else {
                binding.condition.text = ""
            }

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

        viewModel.calendarDate.observe(this.viewLifecycleOwner) { calendarDate ->
            actualDate = calendarDate
            binding.todayDate.text = calendarDate
        }

        viewModel.sortCriteria.observe(this.viewLifecycleOwner) { sortCriteria ->
            when (sortCriteria) {
                SortCriteria.DATE -> {
                    updateNotesList(
                        sortedBy = SortCriteria.DATE,
                        date = actualDate
                    )
                }

                SortCriteria.UNFINISHED -> {
                    updateNotesList(
                        sortedBy = SortCriteria.UNFINISHED,
                        date = actualDate
                    )
                }

                SortCriteria.COMPLETED -> {
                    updateNotesList(
                        sortedBy = SortCriteria.COMPLETED,
                        date = actualDate
                    )
                }
            }
        }

        binding.todayDate.setOnClickListener {
            binding.warningNoteRequired.visibility = View.GONE
            CalendarPicker().show(getParentFragmentManager(), "CALENDAR_PICKER")
        }

        binding.saveNoteBtn.setOnClickListener {
            if (binding.inputText.text.isNullOrEmpty()) {
                binding.warningNoteRequired.visibility = View.VISIBLE
            } else {
                binding.warningNoteRequired.visibility = View.GONE
                viewModel.createNote(
                    binding.todayDate.text.toString(), false,
                    binding.inputText.text.toString()
                )
                actualDate = binding.todayDate.text.toString()
                viewModel.setSortingCriteria(SortCriteria.DATE)
            }
            binding.inputText.text?.clear()
        }

        binding.showFinishedBtn.setOnClickListener {
            binding.warningNoteRequired.visibility = View.GONE
            viewModel.setSortingCriteria(SortCriteria.COMPLETED)
        }

        binding.showUnfinishedBtn.setOnClickListener {
            binding.warningNoteRequired.visibility = View.GONE
            viewModel.setSortingCriteria(SortCriteria.UNFINISHED)
        }
    }

    private fun updateNotesList(
        sortedBy: SortCriteria,
        date: String?
    ) {
        getChildFragmentManager().beginTransaction()
            .replace(
                R.id.recycler_view_fragment_container,
                NotesListFragment.newInstance(
                    sortCriteria = sortedBy.name,
                    date = date
                )
            )
            .commit()
    }

    private fun checkLocationPermissions() {
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
    }
}