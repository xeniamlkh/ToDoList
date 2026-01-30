package com.example.todolist

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

interface PermissionRationaleDialogListener {
    fun onPermissionConfirmation()
    fun onDismissPermission()
}

class PermissionRationaleDialog(private val rationaleListener: PermissionRationaleDialogListener) :
    DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder
                .setTitle(getString(R.string.rationale_title))
                .setMessage(getString(R.string.rationale_message))
                .setIcon(R.drawable.ic_nice_day)
                .setPositiveButton(getString(R.string.ok_btn)) { _, _ ->
                    rationaleListener.onPermissionConfirmation()
                }
                .setNegativeButton(getString(R.string.decline_btn)) { _, _ ->
                    rationaleListener.onDismissPermission()
                }
            builder.create()
        } ?: throw IllegalStateException(getString(R.string.activity_warning))
    }
}