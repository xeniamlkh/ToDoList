package com.example.presentation.noteslist
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.ViewModel
//import javax.inject.Inject

//class NotesListVM @Inject constructor(private val repository: NotesRepositoryImpl) : ViewModel() {
//
//    fun getListOfNotesByDate(date: String): LiveData<List<ToDoListEntity>> {
//        return repository.getListOfNotesByDate(date).asLiveData()
//    }
//
//    fun updateNoteStatus(noteId: Int, checkBoxStatus: Boolean) {
//        viewModelScope.launch {
//            repository.updateNoteStatus(noteId, checkBoxStatus)
//        }
//    }
//
//    fun showAllFinishedTasks(): LiveData<List<ToDoListEntity>> {
//        return repository.showAllFinishedTasks().asLiveData()
//    }
//
//    fun showAllUnfinishedTasks(): LiveData<List<ToDoListEntity>> {
//        return repository.showAllUnfinishedTasks().asLiveData()
//    }
//
//    fun deleteAllFinishedTasks() {
//        viewModelScope.launch { repository.deleteAllFinishedTasks() }
//    }
//}