package com.example.todolist

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.domain.interfaces.LocationService
import com.example.domain.interfaces.WeatherService
import com.example.domain.models.WeatherData
import com.example.presentation.today.TodayFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "!!!!!"

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), PermissionRationaleDialogListener {

    private val apiKey = BuildConfig.API_KEY

    @Inject
    lateinit var locationService: LocationService

    @Inject
    lateinit var weatherService: WeatherService

    //private lateinit var boardArgument: DisplayBoard

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->

            val coarseLocationPermission = result
                .getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false)

            val fineLocationPermission = result
                .getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false)

//            boardArgument = if ((coarseLocationPermission) && (fineLocationPermission)) {
//                //Log.d(TAG, "1")
//                DisplayBoard.WEATHER
//            } else {
//                //Log.d(TAG, "2")
//                DisplayBoard.QUOTE
//            }

            if ((coarseLocationPermission) && (fineLocationPermission)) {
                Log.d(TAG, "1")
                //DisplayBoard.WEATHER
                //TODO Get location
                //TODO Get a forecast
                //TODO Start a fragment with a weather forecast
                getLocation()
            } else {
                //Log.d(TAG, "2")
                //DisplayBoard.QUOTE
                //TODO Start a fragment with a quote
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "onCreate: ")

        if (savedInstanceState == null) {
            checkLocationPermissions()
        } else {
            //TODO Save boardArgument to savedInstanceState and receive it here
        }

        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (supportFragmentManager.backStackEntryCount > 0) {
                        supportFragmentManager.popBackStack()
                    } else {
                        finish()
                    }
                }
            })
    }

    private fun checkLocationPermissions() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                //boardArgument = DisplayBoard.WEATHER
                //TODO Get location
                //TODO Get a forecast
                //TODO Start a fragment with a weather forecast
                Log.d(TAG, "checkLocationPermissions: 2")
                getLocation()
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) -> {
                showRationalDialog()
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
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
        PermissionRationaleDialog(this)
            .show(supportFragmentManager, "RATIONALE")
    }


    override fun onPermissionConfirmation() {
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
    }

    override fun onDismissPermission() {
        //boardArgument = DisplayBoard.QUOTE
    }

    private fun getLocation() {
        lifecycleScope.launch {
            locationService.getLastLocation()?.let { lastLocation ->
                Log.d(TAG, "getLocation: last location = $lastLocation")
                getWeatherForecast(lat = lastLocation.lat.toString(), lon = lastLocation.lon.toString())
            } ?: locationService.getCurrentLocation()?.let { currentLocation ->
                Log.d(TAG, "getLocation: current location = $currentLocation")
                getWeatherForecast(lat = currentLocation.lat.toString(), lon = currentLocation.lon.toString())
            }
        }
    }

    private fun getWeatherForecast(lat: String, lon: String) {
        lifecycleScope.launch {
            val forecast: WeatherData? = weatherService.getCurrentWeather(
                lat = lat,
                lon = lon,
                apiKey = apiKey,
                units = "metric"
            )

            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.today_fragment_container,
                    TodayFragment.newInstance(forecast)
                )
                .commit()
            Log.d(TAG, "getWeatherForecast: forecast = $forecast")
        }
    }

    //TODO pass a quote or a weather forecast
    private fun startFragment() {

//        supportFragmentManager.beginTransaction()
//            .replace(
//                R.id.today_fragment_container,
//                TodayFragment.newInstance(boardArgument)
//            )
//            .commit()
    }
}