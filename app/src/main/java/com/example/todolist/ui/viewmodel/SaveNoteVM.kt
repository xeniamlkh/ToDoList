package com.example.todolist.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.repository.ToDoListRepository
import com.example.todolist.data.room.ToDoListEntity
import kotlinx.coroutines.launch

class SaveNoteVM(private val repository: ToDoListRepository) : ViewModel() {

    fun createNote(date: String, checkboxStatus: Boolean, note: String) {
        val noteRecord = ToDoListEntity(0, date, checkboxStatus, note)
        insertNote(noteRecord)
    }

    private fun insertNote(note: ToDoListEntity) {
        viewModelScope.launch {
            repository.insertNote(note)
        }
    }
}

class SaveNoteVMFactory(private val repository: ToDoListRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(SaveNoteVM::class.java)) {
            return SaveNoteVM(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}