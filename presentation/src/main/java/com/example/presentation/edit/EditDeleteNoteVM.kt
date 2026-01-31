package com.example.presentation.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.interfaces.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditDeleteNoteVM @Inject constructor(private val repository: NotesRepository) :
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