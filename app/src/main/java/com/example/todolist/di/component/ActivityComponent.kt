package com.example.todolist.di.component

import com.example.todolist.di.component.annotation.ActivityScope
import com.example.todolist.di.module.repository.CacheRepositoryModule
import com.example.todolist.di.module.repository.NetworkRepositoryModule
import com.example.todolist.di.module.repository.RoomRepositoryModule
import com.example.todolist.di.module.viewmodel.EditDeleteNoteVMModule
import com.example.todolist.di.module.viewmodel.MainActivityVMModule
import com.example.todolist.di.module.viewmodel.NotesListVMModule
import com.example.todolist.di.module.viewmodel.TodayFragmentVMModule
import com.example.todolist.ui.activities.MainActivity
import com.example.todolist.ui.alertdialogs.DeleteNoteAlertDialog
import com.example.todolist.ui.fragments.EditNoteFragment
import com.example.todolist.ui.fragments.NotesListFragment
import com.example.todolist.ui.fragments.TodayFragment
import com.example.todolist.ui.viewmodel.EditDeleteNoteVM
import com.example.todolist.ui.viewmodel.MainActivityVM
import com.example.todolist.ui.viewmodel.NotesListVM
import com.example.todolist.ui.viewmodel.TodayFragmentVM
import dagger.Subcomponent

@ActivityScope
@Subcomponent(
    modules =
    [
        RoomRepositoryModule::class,
        CacheRepositoryModule::class,
        NetworkRepositoryModule::class,
        EditDeleteNoteVMModule::class,
        MainActivityVMModule::class,
        NotesListVMModule::class,
        TodayFragmentVMModule::class
    ]
)
interface ActivityComponent {

    @Subcomponent.Factory
    interface ActivityComponentFactory {
        fun create(): ActivityComponent
    }

    fun injectEditDeleteNoteVM(editDeleteNoteVM: EditDeleteNoteVM)
    fun injectMainActivityVM(mainActivityVM: MainActivityVM)
    fun injectNotesListVM(notesListVM: NotesListVM)
    fun injectTodayFragmentVM(todayFragmentVM: TodayFragmentVM)

    fun injectActivity(activity: MainActivity)
    fun injectTodayFragment(fragment: TodayFragment)
    fun injectEditNoteFragment(fragment: EditNoteFragment)
    fun injectNotesListFragment(fragment: NotesListFragment)
    fun injectDeleteNoteFragment(fragment: DeleteNoteAlertDialog)

}