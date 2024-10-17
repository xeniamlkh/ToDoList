package com.example.todolist.ui.activities

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.todolist.R
import com.example.todolist.ToDoListApplication
import com.example.todolist.di.component.ActivityComponent
import com.example.todolist.ui.alertdialogs.PermissionRationaleDialog
import com.example.todolist.ui.alertdialogs.PermissionRationaleDialogListener
import com.example.todolist.ui.fragments.TodayFragment
import com.example.todolist.ui.utils.LocationHelper
import com.example.todolist.ui.viewmodel.MainActivityVM
import javax.inject.Inject

private const val FRAGMENT_TAG = "todayFragment"

class MainActivity : AppCompatActivity(), PermissionRationaleDialogListener,
    LocationHelper.LocationUpdateListener {

    @Inject
    lateinit var viewModel: MainActivityVM

    private lateinit var activityComponent: ActivityComponent

    private lateinit var fragment: TodayFragment
    private lateinit var locationHelper: LocationHelper

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

        val appComponent = (application as ToDoListApplication).getAppComponent()
        activityComponent = appComponent.activityComponent().create()
        activityComponent.injectActivity(this)

        locationHelper = LocationHelper(this, this)

        checkLocationPermissions()

        fragment = if (savedInstanceState == null) {
            TodayFragment()
        } else {
            supportFragmentManager.findFragmentByTag(FRAGMENT_TAG) as TodayFragment
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
            locationHelper.startLocationUpdates()
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
        locationHelper.stopLocationUpdates()
    }

    override fun onLocationUpdated(location: Location) {
        val latitude = location.latitude.toString()
        val longitude = location.longitude.toString()

        viewModel.getCurrentWeather(latitude, longitude)
        viewModel.showQuote(false)
    }
}