package com.example.todolist

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.domain.enums.DisplayBoard
import com.example.presentation.today.TodayFragment
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "!!!!!"
private const val FRAGMENT_TAG = "todayFragment"

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), PermissionRationaleDialogListener {

    private lateinit var fragment: TodayFragment
    private lateinit var boardArgument: DisplayBoard

    private val permissionLauncher =
        registerForActivityResult(
            ActivityResultContracts
                .RequestMultiplePermissions()
        ) { result ->

            Log.d(TAG, "1: ")

            val coarseLocationPermission = result
                .getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false)

            val fineLocationPermission = result
                .getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false)

            if ((coarseLocationPermission) && (fineLocationPermission)) {
                boardArgument = DisplayBoard.WEATHER
            } else {
               // DisplayBoard.QUOTE
                showRationalDialog()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate: ...")
        Toast.makeText(this, "test", Toast.LENGTH_SHORT).show()

       // checkLocationPermissions()

//        fragment = if (savedInstanceState == null) {
//            TodayFragment()
//        } else {
//            supportFragmentManager.findFragmentByTag(FRAGMENT_TAG) as TodayFragment
//        }

//        supportFragmentManager.beginTransaction()
//            //.replace(R.id.today_fragment_container, fragment, FRAGMENT_TAG) //TODO pass an arg
//            .replace(R.id.today_fragment_container, TodayFragment.newInstance(DisplayBoard.QUOTE))
//            .commit()

//        onBackPressedDispatcher.addCallback(
//            this,
//            object : OnBackPressedCallback(true) {
//                override fun handleOnBackPressed() {
//                    if (supportFragmentManager.backStackEntryCount > 0) {
//                        supportFragmentManager.popBackStack()
//                    } else {
//                        finish()
//                    }
//                }
//            })
    }

    private fun checkLocationPermissions() {
        Log.d(TAG, "checkLocationPermissions: ???")
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission( // || or && ?
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                Log.d(TAG, "checkLocationPermissions: 2")
                boardArgument = DisplayBoard.WEATHER
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) -> {
                Log.d(TAG, "checkLocationPermissions: 3")
                showRationalDialog()
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
                Log.d(TAG, "checkLocationPermissions: 4")
                showRationalDialog()
            }

            else -> {
                Log.d(TAG, "checkLocationPermissions: 5")
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
        Log.d(TAG, "onDismissPermission: 6")
        boardArgument = DisplayBoard.QUOTE
    }
}