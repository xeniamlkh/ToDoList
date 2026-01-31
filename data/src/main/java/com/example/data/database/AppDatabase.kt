package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

// WeatherEntity::class
//abstract fun weatherDao(): WeatherDao
@Database(
    entities = [NoteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
}