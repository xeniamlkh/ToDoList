package com.example.data.geogms

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.example.domain.interfaces.LocationService
import com.example.domain.models.Coordinates
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LocationServiceImpl @Inject constructor(@param:ApplicationContext private val context: Context) :
    LocationService {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    override suspend fun getLastLocation(): Coordinates? {
        val location: Location? = fusedLocationClient.lastLocation.await()

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
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .build()

        val location: Location? = fusedLocationClient
            .getCurrentLocation(request, cancellationTokenSource.token)
            .await()

        return location?.let {
            Coordinates(
                lat = it.latitude,
                lon = it.longitude
            )
        }
    }
}