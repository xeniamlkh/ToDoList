package com.example.todolist.model.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoListDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(note: ToDoListEntity)

    @Query("DELETE FROM notes_table WHERE id = :noteId")
    suspend fun deleteNoteById(noteId: Int)

    @Query("SELECT * FROM notes_table WHERE date = :date")
    fun getListOfNotesByDate(date: String): Flow<List<ToDoListEntity>>

    @Query("UPDATE notes_table SET note = :noteText WHERE id = :noteId")
    suspend fun updateNoteById(noteId: Int, noteText: String)

    @Query("UPDATE notes_table SET `checkbox status` = :checkBoxStatus WHERE id = :noteId")
    suspend fun updateNoteStatus(noteId: Int, checkBoxStatus: Boolean)

    @Query("SELECT * FROM notes_table WHERE `checkbox status` = 1 ORDER BY date")
    fun showAllFinishedTasks(): Flow<List<ToDoListEntity>>

    @Query("SELECT * FROM notes_table WHERE `checkbox status` = 0 ORDER BY date")
    fun showAllUnfinishedTasks(): Flow<List<ToDoListEntity>>

    @Query("DELETE FROM notes_table WHERE `checkbox status` = 1")
    suspend fun deleteAllFinishedTasks()
}