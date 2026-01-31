package com.example.data.repository

//import com.example.data.database.NotesDao
//import com.example.data.database.toEntity
//import com.example.data.database.toNote
//import com.example.domain.models.Note
//import com.example.domain.interfaces.NotesRepository
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.map
//import javax.inject.Inject
//
//class NotesRepositoryImpl @Inject constructor(private val notesDao: NotesDao) : NotesRepository {
//
//    override suspend fun insertNote(note: Note) {
//        notesDao.insertNote(note.toEntity())
//    }
//
//    override suspend fun deleteNoteById(noteId: Int) {
//        notesDao.deleteNoteById(noteId)
//    }
//
//    override fun getListOfNotesByDate(date: String): Flow<List<Note>> =
//        notesDao.getListOfNotesByDate(date).map { it.map { noteEntity -> noteEntity.toNote() } }
//
//
//    override suspend fun updateNoteById(noteId: Int, noteText: String) {
//        notesDao.updateNoteById(noteId, noteText)
//    }
//
//    override suspend fun updateNoteStatus(noteId: Int, checkBoxStatus: Boolean) {
//        notesDao.updateNoteStatus(noteId, checkBoxStatus)
//    }
//
//    override fun showAllFinishedTasks(): Flow<List<Note>> =
//        notesDao.showAllFinishedTasks().map {
//            it.map { noteEntity -> noteEntity.toNote() }
//        }
//
//    override fun showAllUnfinishedTasks(): Flow<List<Note>> =
//        notesDao.showAllUnfinishedTasks().map {
//            it.map { noteEntity -> noteEntity.toNote() }
//        }
//
//    override suspend fun deleteAllFinishedTasks() {
//        notesDao.deleteAllFinishedTasks()
//    }
//}