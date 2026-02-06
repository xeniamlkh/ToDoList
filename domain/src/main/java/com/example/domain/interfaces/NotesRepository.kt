package com.example.domain.interfaces

import com.example.domain.models.Note
import kotlinx.coroutines.flow.Flow

interface NotesRepository {

    suspend fun insertNote(note: Note)

    suspend fun deleteNoteById(noteId: Int)

    fun getListOfNotesByDate(date: String): Flow<List<Note>>

    suspend fun updateNoteById(noteId: Int, noteText: String)

    suspend fun updateNoteStatus(noteId: Int, checkBoxStatus: Boolean)

    fun showAllFinishedTasks(): Flow<List<Note>>

    fun showAllUnfinishedTasks(): Flow<List<Note>>

    suspend fun deleteAllFinishedTasks()
}