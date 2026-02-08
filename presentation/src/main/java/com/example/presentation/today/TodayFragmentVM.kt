package com.example.presentation.today

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.enums.SortCriteria
import com.example.domain.interfaces.LocationService
import com.example.domain.interfaces.NotesRepository
import com.example.domain.interfaces.WeatherService
import com.example.domain.models.Coordinates
import com.example.domain.models.Note
import com.example.domain.models.WeatherData
import com.example.presentation.BuildConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class TodayFragmentVM @Inject constructor(
    private val repository: NotesRepository,
    private val locationService: LocationService,
    private val weatherService: WeatherService
) : ViewModel() {

    private val apiKey = BuildConfig.API_KEY

    private val _calendarDate = MutableLiveData<String>()
    val calendarDate: LiveData<String> get() = _calendarDate

    private val _sortCriteria = MutableLiveData<SortCriteria>()
    val sortCriteria: LiveData<SortCriteria> get() = _sortCriteria

    private val _permissionConfirmationStatus = MutableLiveData<Boolean>()
    val permissionConfirmationStatus: LiveData<Boolean> get() = _permissionConfirmationStatus

    private val _locationError = MutableLiveData<Boolean>()
    val locationError: LiveData<Boolean> get() = _locationError

    private val _networkError = MutableLiveData<Boolean>()
    val networkError: LiveData<Boolean> get() = _networkError

    private val _quoteStatus = MutableLiveData<Boolean>()
    val quoteStatus: LiveData<Boolean> get() = _quoteStatus

    private var _weatherConditions = MutableLiveData<String>()
    val weatherConditions: LiveData<String> get() = _weatherConditions

    private var _city = MutableLiveData<String>()
    val city: LiveData<String> get() = _city

    private var _temperature = MutableLiveData<Int>()
    val temperature: LiveData<Int> get() = _temperature

    fun setCalendarDate(calendarDate: String) {
        _calendarDate.value = calendarDate

        setSortingCriteria(SortCriteria.DATE)
    }

    fun setSortingCriteria(sortCriteria: SortCriteria) {
        _sortCriteria.value = sortCriteria
    }

    fun createNote(date: String, checkboxStatus: Boolean, text: String) {
        val note = Note(0, date = date, checkboxStatus = checkboxStatus, text = text)
        viewModelScope.launch {
            repository.insertNote(note)
        }
    }

    fun setPermissionConfirmationStatus(status: Boolean) {
        _permissionConfirmationStatus.value = status
    }

    fun setQuoteStatus(status: Boolean) {
        _quoteStatus.value = status
    }

    fun getActualForecast() {
        viewModelScope.launch(Dispatchers.IO) {
            val coordinates = getLocation()
            if (coordinates != null) {
                getWeatherForecast(
                    lat = coordinates.lat.toString(),
                    lon = coordinates.lon.toString()
                )
            } else {
                withContext(Dispatchers.Main) {
                    _locationError.value = true
                    _quoteStatus.value = true
                }
            }
        }
    }

    private suspend fun getLocation(): Coordinates? {
        val coordinates = locationService.getLastLocation() ?: locationService.getCurrentLocation()
        return coordinates
    }

    private suspend fun getWeatherForecast(lat: String, lon: String) {
        try {
            val forecast: WeatherData? = weatherService.getCurrentWeather(
                lat = lat,
                lon = lon,
                apiKey = apiKey,
                units = "metric"
            )

            if (forecast != null) {
                withContext(Dispatchers.Main) {
                    _weatherConditions.value = forecast.weather[0].main
                    _city.value = forecast.name
                    _temperature.value = forecast.main.temp.roundToInt()
                    _locationError.value = false
                    _networkError.value = false
                    _quoteStatus.value = false
                }
            } else {
                withContext(Dispatchers.Main) {
                    _networkError.value = true
                    _quoteStatus.value = true
                }
            }

        } catch (_: Exception) {
            withContext(Dispatchers.Main) {
                _networkError.value = true
                _quoteStatus.value = true
            }
        }
    }
}