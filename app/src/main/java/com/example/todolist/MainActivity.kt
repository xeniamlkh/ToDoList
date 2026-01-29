package com.example.todolist

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.domain.enums.DisplayBoard
import com.example.presentation.today.TodayFragment
import dagger.hilt.android.AndroidEntryPoint

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

            val coarseLocationPermission = result
                .getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false)

            val fineLocationPermission = result
                .getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false)

            if ((coarseLocationPermission) && (fineLocationPermission)) {
                boardArgument = DisplayBoard.WEATHER
            } else {
                boardArgument = DisplayBoard.QUOTE
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkLocationPermissions()

//        fragment = if (savedInstanceState == null) {
//            TodayFragment()
//        } else {
//            supportFragmentManager.findFragmentByTag(FRAGMENT_TAG) as TodayFragment
//        }

        supportFragmentManager.beginTransaction()
            //.replace(R.id.today_fragment_container, fragment, FRAGMENT_TAG) //TODO pass an arg
            .replace(R.id.today_fragment_container, TodayFragment.newInstance(boardArgument))
            .commit()

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
            ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission( // || or && ?
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                boardArgument = DisplayBoard.WEATHER
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
        boardArgument = DisplayBoard.QUOTE
    }
}