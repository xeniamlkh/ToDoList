package com.example.todolist

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

interface PermissionRationaleDialogListener {
    fun callPermissionLauncher()
    fun getQuote()
}

class PermissionRationaleDialog(private val rationaleListener: PermissionRationaleDialogListener) :
    DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(getString(R.string.rationale_message))
                .setIcon(R.drawable.ic_nice_day)
                .setTitle(getString(R.string.rationale_title))
                .setPositiveButton(getString(R.string.ok_btn)) { _, _ ->
                    rationaleListener.callPermissionLauncher()
                }
                .setNegativeButton(getString(R.string.decline_btn)) { _, _ ->
                    rationaleListener.getQuote()
                }
            builder.create()
        } ?: throw IllegalStateException(getString(R.string.activity_warning))
    }
}