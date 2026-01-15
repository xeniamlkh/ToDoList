package com.example.todolist.di.module.viewmodel

import com.example.todolist.data.repository.ToDoListRepository
import com.example.todolist.di.component.annotation.ActivityScope
import com.example.todolist.ui.viewmodel.EditDeleteNoteVM
import com.example.todolist.ui.viewmodel.EditDeleteNoteVMFactory
import dagger.Module
import dagger.Provides
import dagger.internal.Provider

// assistant!

@Module
class EditDeleteNoteVMModule {

    @ActivityScope
    @Provides
    fun getEditDeleteNoteViewModel(repository: ToDoListRepository): EditDeleteNoteVM {
        return EditDeleteNoteVM(repository)
    }

    @ActivityScope
    @Provides
    fun provideViewModelProvider(viewModel: EditDeleteNoteVM): Provider<EditDeleteNoteVM> {
        return Provider { viewModel }
    }

    @ActivityScope
    @Provides
    fun getViewModelFactory(provider: Provider<EditDeleteNoteVM>): EditDeleteNoteVMFactory {
        return EditDeleteNoteVMFactory(provider)
    }
}