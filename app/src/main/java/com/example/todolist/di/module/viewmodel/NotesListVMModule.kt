package com.example.todolist.di.module.viewmodel

import com.example.todolist.data.repository.ToDoListRepository
import com.example.todolist.di.component.annotation.ActivityScope
import com.example.todolist.ui.viewmodel.NotesListVM
import com.example.todolist.ui.viewmodel.NotesListVMFactory
import dagger.Module
import dagger.Provides
import dagger.internal.Provider

@Module
class NotesListVMModule {

    @ActivityScope
    @Provides
    fun getNotesListViewModel(repository: ToDoListRepository): NotesListVM {
        return NotesListVM(repository)
    }

    @ActivityScope
    @Provides
    fun provideViewModelProvider(viewModel: NotesListVM): Provider<NotesListVM> {
        return Provider { viewModel }
    }

    @ActivityScope
    @Provides
    fun getViewModelFactory(provider: Provider<NotesListVM>): NotesListVMFactory {
        return NotesListVMFactory(provider)
    }
}