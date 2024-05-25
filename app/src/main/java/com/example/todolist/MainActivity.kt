package com.example.todolist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.example.todolist.ui.alertdialogs.PermissionRationaleDialog
import com.example.todolist.ui.alertdialogs.PermissionRationaleDialogListener
import com.example.todolist.ui.ui.TodayFragment
import com.example.todolist.ui.viewmodel.ToDoListViewModel
import com.example.todolist.ui.viewmodel.ToDoListViewModelFactory

private const val FRAGMENT_TAG = "todayFragment"

class MainActivity : AppCompatActivity(), PermissionRationaleDialogListener {

    private val viewModel: ToDoListViewModel by viewModels {
        ToDoListViewModelFactory(
            (application as ToDoListApplication).repository
        )
    }

    private lateinit var fragment: TodayFragment

    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->

            val coarseLocationPermission =
                result.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false)
            val fineLocationPermission =
                result.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false)

            if ((coarseLocationPermission) && (fineLocationPermission)) {
                getLocation()
            } else {
                getQuote()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                viewModel.getCurrentWeather(
                    location.latitude.toString(),
                    location.longitude.toString()
                )
                viewModel.showQuote(false)
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
        }

        checkLocationPermissions()

        if (savedInstanceState == null) {
            fragment = TodayFragment()
        } else {
            fragment = supportFragmentManager.findFragmentByTag(FRAGMENT_TAG) as TodayFragment
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.today_fragment_container, fragment, FRAGMENT_TAG)
            .commit()

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
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
            (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED) -> {
                getLocation()
            }

            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) -> {
                showExplanation()
            }

            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                showExplanation()
            }

            else -> {
                requestLocationPermissions()
            }
        }
    }

    private fun requestLocationPermissions() {
        requestPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
    }

    private fun showExplanation() {
        PermissionRationaleDialog(this).show(supportFragmentManager, "RATIONALE")
    }

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        } else {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 5000L, 10f, locationListener
            )
        }

    }

    private fun loadQuote() {
        viewModel.showQuote(true)
    }

    override fun callPermissionLauncher() {
        requestLocationPermissions()
    }

    override fun getQuote() {
        loadQuote()
    }

    override fun onDestroy() {
        super.onDestroy()
        locationManager.removeUpdates(locationListener)
    }
}