package com.example.todolist.data.repository

import com.example.todolist.data.room.dao.ToDoListDao
import com.example.todolist.data.room.entity.ToDoListEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ToDoListRepository @Inject constructor(private val notesDao: ToDoListDao) {

    suspend fun insertNote(note: ToDoListEntity) {
        notesDao.insertNote(note)
    }

    suspend fun deleteNoteById(noteId: Int) {
        notesDao.deleteNoteById(noteId)
    }

    fun getListOfNotesByDate(date: String): Flow<List<ToDoListEntity>> {
        return notesDao.getListOfNotesByDate(date)
    }

    suspend fun updateNoteById(noteId: Int, noteText: String) {
        notesDao.updateNoteById(noteId, noteText)
    }

    suspend fun updateNoteStatus(noteId: Int, checkBoxStatus: Boolean) {
        notesDao.updateNoteStatus(noteId, checkBoxStatus)
    }

    fun showAllFinishedTasks(): Flow<List<ToDoListEntity>> {
        return notesDao.showAllFinishedTasks()
    }

    fun showAllUnfinishedTasks(): Flow<List<ToDoListEntity>> {
        return notesDao.showAllUnfinishedTasks()
    }

    suspend fun deleteAllFinishedTasks() {
        notesDao.deleteAllFinishedTasks()
    }
}