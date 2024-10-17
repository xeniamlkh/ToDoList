package com.example.todolist.di.component

import com.example.todolist.data.repository.ToDoListRepository
import com.example.todolist.data.repository.WeatherCacheRepository
import com.example.todolist.data.repository.WeatherNetworkRepository
import com.example.todolist.di.module.service.RoomServiceModule
import com.example.todolist.di.module.service.NetworkServiceModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkServiceModule::class, RoomServiceModule::class])
interface AppComponent {

    fun injectNetworkRepository(networkRepository: WeatherNetworkRepository)
    fun injectToDoListRepository(toDoListRepository: ToDoListRepository)
    fun injectCacheRepository(cacheRepository: WeatherCacheRepository)

    fun activityComponent(): ActivityComponent.ActivityComponentFactory
}