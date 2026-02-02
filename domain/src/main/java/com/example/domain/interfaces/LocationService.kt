package com.example.domain.interfaces

import com.example.domain.models.Coordinates

interface LocationService {
    suspend fun getLastLocation(): Coordinates?
    suspend fun getCurrentLocation(): Coordinates?
}