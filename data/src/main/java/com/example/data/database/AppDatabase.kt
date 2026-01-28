package com.example.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [NoteEntity::class, WeatherEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun notesDao(): NotesDao
    abstract fun weatherDao(): WeatherDao

//    companion object {
//        @Volatile
//        private var INSTANCE: NotesDatabase? = null
//
//        fun getDatabase(context: Context): NotesDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    NotesDatabase::class.java,
//                    "notes_database"
//                ).build()
//
//                INSTANCE = instance
//
//                instance
//            }
//        }
//    }
}