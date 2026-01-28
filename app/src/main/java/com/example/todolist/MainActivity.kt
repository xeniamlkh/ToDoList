package com.example.todolist

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.presentation.today.TodayFragment
import dagger.hilt.android.AndroidEntryPoint

private const val FRAGMENT_TAG = "todayFragment"

//LocationHelper.LocationUpdateListener
//PermissionRationaleDialogListener
//TODO Temporary remove LocationHelper.LocationUpdateListener & PermissionRationaleDialogListener- return later!
//TODO Rewrite permisiion request
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    //@Inject lateinit var viewModel: MainActivityVM
    //lateinit var viewModel: MainActivityVM

    private val viewModel: MainActivityVM by viewModels()

    private lateinit var fragment: TodayFragment

    //TODO Temporary
   // private lateinit var locationHelper: LocationHelper
//    private val requestPermissionLauncher =
//        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
//
//            val coarseLocationPermission =
//                result.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false)
//            val fineLocationPermission =
//                result.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false)
//
//            if ((coarseLocationPermission) && (fineLocationPermission)) {
//                getLocation()
//            } else {
//                getQuote()
//            }
//        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO Temporary
        //locationHelper = LocationHelper(this, this)
        //checkLocationPermissions()
        viewModel.showQuote(true) //TODO remove from here - just testing

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

    //TODO Temporary
//    private fun checkLocationPermissions() {
//        when {
//            (ContextCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED &&
//                    ContextCompat.checkSelfPermission(
//                        this,
//                        Manifest.permission.ACCESS_FINE_LOCATION
//                    ) == PackageManager.PERMISSION_GRANTED) -> {
//                getLocation()
//            }
//
//            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) -> {
//                showExplanation()
//            }
//
//            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
//                showExplanation()
//            }
//
//            else -> {
//                //requestLocationPermissions()
//                callPermissionLauncher()
//            }
//        }
//    }

    //TODO Temporary
//    private fun showExplanation() {
//        PermissionRationaleDialog(this).show(supportFragmentManager, "RATIONALE")
//    }

    //TODO Temporary
//    private fun getLocation() {
//        if (ActivityCompat.checkSelfPermission(
//                this, Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                this, Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            return
//        } else {
//            locationHelper.startLocationUpdates()
//        }
//    }

    //TODO Temporary
//    override fun callPermissionLauncher() {
//        requestPermissionLauncher.launch(
//            arrayOf(
//                Manifest.permission.ACCESS_COARSE_LOCATION,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            )
//        )
//
//        //requestLocationPermissions()
//    }

    //TODO Temporary
//    override fun getQuote() {
//        viewModel.showQuote(true)
//    }

    override fun onDestroy() {
        super.onDestroy()
        //TODO Temporary
        //locationHelper.stopLocationUpdates()
    }

    //TODO Temporary
//    override fun onLocationUpdated(location: Location) {
//        val latitude = location.latitude.toString()
//        val longitude = location.longitude.toString()
//
//        viewModel.getCurrentWeather(latitude, longitude)
//        viewModel.showQuote(false)
//    }
}