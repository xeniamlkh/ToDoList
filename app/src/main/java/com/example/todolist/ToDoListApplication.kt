package com.example.todolist

import android.app.Application
import com.example.todolist.model.data.ToDoListDatabase
import com.example.todolist.model.repository.ToDoListRepository

class ToDoListApplication : Application() {
    val database: ToDoListDatabase by lazy { ToDoListDatabase.getDatabase(this) }
    val repository: ToDoListRepository by lazy { ToDoListRepository(database.notesDao()) }
}