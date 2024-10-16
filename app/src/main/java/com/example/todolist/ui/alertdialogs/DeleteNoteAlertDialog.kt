package com.example.todolist.ui.alertdialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.todolist.R
import com.example.todolist.ToDoListApplication
import com.example.todolist.di.component.ActivityComponent
import com.example.todolist.ui.viewmodel.EditDeleteNoteVM
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

private const val ARG_PARAM_NOTE_ID = "noteId"

class DeleteNoteAlertDialog : DialogFragment() {

    @Inject
    lateinit var viewModel: EditDeleteNoteVM

    private lateinit var activityComponent: ActivityComponent

    private var noteId: Int = -1

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val appComponent = (requireActivity().application as ToDoListApplication).getAppComponent()
        activityComponent = appComponent.activityComponent().create()
        activityComponent.injectDeleteNoteFragment(this)

        arguments?.let {
            noteId = it.getInt(ARG_PARAM_NOTE_ID)
        }

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(
                getString(R.string.warning_delete_note)
            )
                .setPositiveButton(getString(R.string.delete_cap)) { _, _ ->
                    if (noteId >= 0) {
                        viewModel.deleteNoteById(noteId)
                    }

                    Snackbar.make(
                        requireActivity().findViewById(android.R.id.content),
                        R.string.note_deleted, Snackbar.LENGTH_SHORT
                    )
                        .show()
                }
                .setNegativeButton(getString(R.string.cancel_cap)) { _, _ -> }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    companion object {
        fun newInstance(noteId: Int) = DeleteNoteAlertDialog().apply {
            arguments = Bundle().apply {
                putInt(ARG_PARAM_NOTE_ID, noteId)
            }
        }
    }
}