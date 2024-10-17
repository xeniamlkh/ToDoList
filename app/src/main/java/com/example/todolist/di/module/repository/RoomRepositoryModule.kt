package com.example.todolist.di.module.repository

import com.example.todolist.data.repository.ToDoListRepository
import com.example.todolist.data.room.dao.ToDoListDao
import com.example.todolist.di.component.annotation.ActivityScope
import dagger.Module
import dagger.Provides

@Module
class RoomRepositoryModule {

    @ActivityScope
    @Provides
    fun getRoomRepository(notesDao: ToDoListDao): ToDoListRepository{
        return ToDoListRepository(notesDao)
    }
}