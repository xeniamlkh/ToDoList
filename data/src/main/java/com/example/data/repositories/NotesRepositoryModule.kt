package com.example.data.repositories

import com.example.domain.interfaces.NotesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NotesRepositoryModule {

    @Binds
    abstract fun bindNotesRepository(impl: NotesRepositoryImpl): NotesRepository
}