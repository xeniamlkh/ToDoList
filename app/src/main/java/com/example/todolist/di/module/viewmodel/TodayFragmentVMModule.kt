package com.example.todolist.di.module.viewmodel

import com.example.todolist.data.repository.ToDoListRepository
import com.example.todolist.data.repository.WeatherCacheRepository
import com.example.todolist.di.component.annotation.ActivityScope
import com.example.todolist.ui.viewmodel.TodayFragmentVM
import com.example.todolist.ui.viewmodel.TodayFragmentVMFactory
import dagger.Module
import dagger.Provides
import dagger.internal.Provider

@Module
class TodayFragmentVMModule {

    @ActivityScope
    @Provides
    fun getTodayFragmentViewModel(
        toDoListRepository: ToDoListRepository,
        weatherCacheRepository: WeatherCacheRepository
    ): TodayFragmentVM {
        return TodayFragmentVM(toDoListRepository, weatherCacheRepository)
    }

    @ActivityScope
    @Provides
    fun provideViewModelProvider(viewModel: TodayFragmentVM): Provider<TodayFragmentVM> {
        return Provider { viewModel }
    }

    @ActivityScope
    @Provides
    fun getViewModelFactory(provider: Provider<TodayFragmentVM>): TodayFragmentVMFactory {
        return TodayFragmentVMFactory(provider)
    }
}