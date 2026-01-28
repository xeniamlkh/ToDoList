package com.example.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(note: NoteEntity)

    @Query("DELETE FROM notes_table WHERE id = :noteId")
    suspend fun deleteNoteById(noteId: Int)

    @Query("SELECT * FROM notes_table WHERE date = :date")
    fun getListOfNotesByDate(date: String): Flow<List<NoteEntity>>

    @Query("UPDATE notes_table SET text = :noteText WHERE id = :noteId")
    suspend fun updateNoteById(noteId: Int, noteText: String)

    @Query("UPDATE notes_table SET `checkbox_status` = :checkBoxStatus WHERE id = :noteId")
    suspend fun updateNoteStatus(noteId: Int, checkBoxStatus: Boolean)

    @Query("SELECT * FROM notes_table WHERE `checkbox_status` = 1 ORDER BY date")
    fun showAllFinishedTasks(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes_table WHERE `checkbox_status` = 0 ORDER BY date")
    fun showAllUnfinishedTasks(): Flow<List<NoteEntity>>

    @Query("DELETE FROM notes_table WHERE `checkbox_status` = 1")
    suspend fun deleteAllFinishedTasks()
}