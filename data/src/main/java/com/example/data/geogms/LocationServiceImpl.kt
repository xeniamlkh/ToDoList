package com.example.data.geogms

import android.annotation.SuppressLint
import android.app.Application
import android.location.Location
import android.util.Log
import com.example.domain.interfaces.LocationService
import com.example.domain.models.Coordinates
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val TAG = "!!!!!"
class LocationServiceImpl @Inject constructor(private val application: Application) :
    LocationService {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application)

    @SuppressLint("MissingPermission")
    override suspend fun getLastLocation(): Coordinates? {
        val location: Location? = fusedLocationClient.lastLocation.await()
        Log.d(TAG, "getLastLocation: last location = $location")

        return location?.let {
            Coordinates(
                lat = it.latitude,
                lon = it.longitude
            )
        }
    }

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): Coordinates? {

        val cancellationTokenSource = CancellationTokenSource()

        val request = CurrentLocationRequest
            .Builder()
            .setPriority(Priority.PRIORITY_LOW_POWER)
            .build()

        val location: Location? = fusedLocationClient
            .getCurrentLocation(request, cancellationTokenSource.token)
            .await()

        Log.d(TAG, "getCurrentLocation: current location = $location")

        return location?.let {
            Coordinates(
                lat = it.latitude,
                lon = it.longitude
            )
        }
    }
}