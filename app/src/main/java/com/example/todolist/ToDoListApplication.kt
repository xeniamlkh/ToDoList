package com.example.todolist

import android.app.Application
import com.example.todolist.data.room.ToDoListDatabase
import com.example.todolist.data.repository.ToDoListRepository

class ToDoListApplication : Application() {
    val database: ToDoListDatabase by lazy { ToDoListDatabase.getDatabase(this) }
    val repository: ToDoListRepository by lazy { ToDoListRepository(database.notesDao()) }
}