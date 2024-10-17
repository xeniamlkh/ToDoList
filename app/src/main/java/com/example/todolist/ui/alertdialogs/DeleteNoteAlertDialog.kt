package com.example.todolist.ui.alertdialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.todolist.R
import com.example.todolist.ToDoListApplication
import com.example.todolist.ui.viewmodel.EditDeleteNoteVM
import com.example.todolist.ui.viewmodel.EditDeleteNoteVMFactory
import com.google.android.material.snackbar.Snackbar

private const val ARG_PARAM_NOTE_ID = "noteId"

class DeleteNoteAlertDialog : DialogFragment() {

//    private val viewModel: EditDeleteNoteVM by activityViewModels {
//        EditDeleteNoteVMFactory(
//            (activity?.application as ToDoListApplication).toDoListRepository
//        )
//    }

    private var noteId: Int = -1

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

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
                        //viewModel.deleteNoteById(noteId)
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