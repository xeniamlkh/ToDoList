package com.example.todolist.ui.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity.LOCATION_SERVICE
import android.location.Location
import android.os.Bundle

// context as a parameter
class LocationHelper(context: Context, updateListener: LocationUpdateListener) {

    private var locationManager: LocationManager =
        context.getSystemService(LOCATION_SERVICE) as LocationManager

    private var locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            updateListener.onLocationUpdated(location)
        }

        @Deprecated("Deprecated in Java")
        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
    }

    @SuppressLint("MissingPermission")
    fun startLocationUpdates() {
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            5000L,
            10f,
            locationListener
        )
    }

    fun stopLocationUpdates() {
        locationManager.removeUpdates(locationListener)
    }

    interface LocationUpdateListener {
        fun onLocationUpdated(location: Location)
    }
}