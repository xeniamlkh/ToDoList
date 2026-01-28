package com.example.todolist.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.repository.ToDoListRepository
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