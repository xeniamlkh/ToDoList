package com.example.todolist.di.module.viewmodel

import com.example.todolist.data.repository.WeatherCacheRepository
import com.example.todolist.data.repository.WeatherNetworkRepository
import com.example.todolist.di.component.annotation.ActivityScope
import com.example.todolist.ui.viewmodel.MainActivityVM
import com.example.todolist.ui.viewmodel.MainActivityVMFactory
import dagger.Module
import dagger.Provides
import dagger.internal.Provider

@Module
class MainActivityVMModule {

    @ActivityScope
    @Provides
    fun getMainActivityViewModel(
        weatherCacheRepository: WeatherCacheRepository,
        weatherNetworkRepository: WeatherNetworkRepository
    ): MainActivityVM {
        return MainActivityVM(weatherCacheRepository, weatherNetworkRepository)
    }

    @ActivityScope
    @Provides
    fun provideViewModelProvider(viewModel: MainActivityVM): Provider<MainActivityVM> {
        return Provider { viewModel }
    }

    @ActivityScope
    @Provides
    fun getViewModelFactory(provider: Provider<MainActivityVM>): MainActivityVMFactory {
        return MainActivityVMFactory(provider)
    }
}