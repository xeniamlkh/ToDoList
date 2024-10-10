package com.example.todolist.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.room.ToDoListEntity
import com.example.todolist.data.repository.ToDoListRepository
import kotlinx.coroutines.launch

class NotesListVM(private val repository: ToDoListRepository) : ViewModel() {

    fun getListOfNotesByDate(date: String): LiveData<List<ToDoListEntity>> {
        return repository.getListOfNotesByDate(date).asLiveData()
    }

    fun updateNoteStatus(noteId: Int, checkBoxStatus: Boolean) {
        viewModelScope.launch {
            repository.updateNoteStatus(noteId, checkBoxStatus)
        }
    }

    fun showAllFinishedTasks(): LiveData<List<ToDoListEntity>> {
        return repository.showAllFinishedTasks().asLiveData()
    }

    fun showAllUnfinishedTasks(): LiveData<List<ToDoListEntity>> {
        return repository.showAllUnfinishedTasks().asLiveData()
    }

    fun deleteAllFinishedTasks() {
        viewModelScope.launch { repository.deleteAllFinishedTasks() }
    }
}

class NotesListVMFactory(private val repository: ToDoListRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(NotesListVM::class.java)) {
            return NotesListVM(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}