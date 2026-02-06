package com.example.presentation.today

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.presentation.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PermissionRationaleDialog() : DialogFragment() {

    private val viewModel: TodayFragmentVM by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder
                .setTitle(getString(R.string.rationale_title))
                .setMessage(getString(R.string.rationale_message))
                .setIcon(R.drawable.ic_nice_day)
                .setPositiveButton(getString(R.string.ok_btn)) { _, _ ->
                    viewModel.setPermissionConfirmationStatus(true)
                }
                .setNegativeButton(getString(R.string.decline_btn)) { _, _ ->
                    viewModel.setQuoteStatus(true)
                }
            builder.create()
        } ?: throw IllegalStateException(getString(R.string.activity_warning))
    }
}