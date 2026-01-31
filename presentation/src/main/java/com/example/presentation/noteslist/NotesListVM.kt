package com.example.presentation.noteslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.interfaces.NotesRepository
import com.example.domain.models.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesListVM @Inject constructor(private val repository: NotesRepository) : ViewModel() {

    fun getListOfNotesByDate(date: String): LiveData<List<Note>> {
        return repository.getListOfNotesByDate(date).asLiveData()
    }

    fun updateNoteStatus(noteId: Int, checkBoxStatus: Boolean) {
        viewModelScope.launch {
            repository.updateNoteStatus(noteId, checkBoxStatus)
        }
    }

    fun showAllFinishedTasks(): LiveData<List<Note>> {
        return repository.showAllFinishedTasks().asLiveData()
    }

    fun showAllUnfinishedTasks(): LiveData<List<Note>> {
        return repository.showAllUnfinishedTasks().asLiveData()
    }

    fun deleteAllFinishedTasks() {
        viewModelScope.launch { repository.deleteAllFinishedTasks() }
    }
}