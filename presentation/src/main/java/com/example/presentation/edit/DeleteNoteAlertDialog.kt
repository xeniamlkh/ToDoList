package com.example.presentation.edit

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.presentation.R
import com.example.presentation.noteslist.NotesVM
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteNoteAlertDialog() : DialogFragment() {

    private val viewModel: NotesVM by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder
                .setMessage(getString(R.string.warning_delete_note))
                .setPositiveButton(getString(R.string.delete_cap)) { _, _ ->
                    viewModel.confirmDeleteNote()

                    Snackbar.make(
                        requireActivity().findViewById(android.R.id.content),
                        R.string.note_deleted, Snackbar.LENGTH_SHORT
                    )
                        .show()
                }
                .setNegativeButton(getString(R.string.cancel_cap)) { _, _ -> }
            builder.create()
        } ?: throw IllegalStateException(getString(R.string.activity_warning))
    }
}