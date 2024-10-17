package com.example.todolist

import android.app.Application
import com.example.todolist.di.component.AppComponent
import com.example.todolist.di.component.DaggerAppComponent
import com.example.todolist.di.module.service.NetworkServiceModule
import com.example.todolist.di.module.service.RoomServiceModule


class ToDoListApplication : Application() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .roomServiceModule(RoomServiceModule(this))
            .networkServiceModule(NetworkServiceModule())
            .build()
    }

    fun getAppComponent(): AppComponent {
        return appComponent
    }
}