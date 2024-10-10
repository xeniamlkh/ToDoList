package com.example.todolist.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.repository.ToDoListRepository
import kotlinx.coroutines.launch

class EditDeleteNoteVM(private val repository: ToDoListRepository) : ViewModel() {

    fun deleteNoteById(noteId: Int) {
        viewModelScope.launch {
            repository.deleteNoteById(noteId)
        }
    }

    fun updateNoteById(noteId: Int, noteText: String) {
        viewModelScope.launch {
            repository.updateNoteById(noteId, noteText)
        }
    }

}

class EditDeleteNoteVMFactory(private val repository: ToDoListRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(EditDeleteNoteVM::class.java)) {
            return EditDeleteNoteVM(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}