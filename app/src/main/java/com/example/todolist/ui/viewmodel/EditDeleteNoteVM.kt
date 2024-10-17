package com.example.todolist.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.repository.ToDoListRepository
import dagger.internal.Provider
import kotlinx.coroutines.launch
import javax.inject.Inject

class EditDeleteNoteVM @Inject constructor(private val repository: ToDoListRepository) :
    ViewModel() {

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

class EditDeleteNoteVMFactory @Inject constructor(private val provider: Provider<EditDeleteNoteVM>) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(EditDeleteNoteVM::class.java)) {
            return provider.get() as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}