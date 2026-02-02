package com.example.data.geogms

import com.example.domain.interfaces.LocationService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationServiceModule {

    @Binds
    abstract fun bindLocationService(impl: LocationServiceImpl): LocationService
}