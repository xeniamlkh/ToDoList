package com.example.data.database

//import android.content.Context
//import androidx.room.Room
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.qualifiers.ApplicationContext
//import dagger.hilt.components.SingletonComponent
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//object AppDatabaseModule {
//
//    @Volatile
//    private var INSTANCE: AppDatabase? = null
//
//    @Provides
//    @Singleton
//    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
//        return INSTANCE ?: synchronized(this) {
//            val instance = Room.databaseBuilder(
//                appContext,
//                AppDatabase::class.java,
//                "notes_database"
//            ).build()
//
//            INSTANCE = instance
//
//            instance
//        }
//    }
//
//    @Provides
//    fun provideNotesDao(database: AppDatabase): NotesDao {
//        return database.notesDao()
//    }
//}