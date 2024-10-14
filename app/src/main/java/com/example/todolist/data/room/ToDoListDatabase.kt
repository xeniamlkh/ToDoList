package com.example.todolist.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ToDoListEntity::class, WeatherEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ToDoListDatabase : RoomDatabase() {

    abstract fun notesDao(): ToDoListDao
    abstract fun weatherDao(): WeatherDao

    companion object {
        @Volatile
        private var INSTANCE: ToDoListDatabase? = null

        fun getDatabase(context: Context): ToDoListDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ToDoListDatabase::class.java,
                    "notes_database"
                ).build()

                INSTANCE = instance

                instance
            }
        }
    }
}