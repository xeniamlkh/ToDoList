package com.example.todolist.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.ToDoListApplication
import com.example.todolist.databinding.FragmentEditNoteBinding
import com.example.todolist.ui.viewmodel.EditDeleteNoteVM
import com.example.todolist.ui.viewmodel.EditDeleteNoteVMFactory
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

private const val ARG_PARAM_NOTE_ID = "noteId"
private const val ARG_PARAM_NOTE_STRING = "noteString"

class EditNoteFragment : BaseFragment<FragmentEditNoteBinding>() {

    private lateinit var callback: OnBackPressedCallback

    private val viewModel: EditDeleteNoteVM by activityViewModels {
        EditDeleteNoteVMFactory(
            (activity?.application as ToDoListApplication).repository
        )
    }

    private var noteId: Int? = null
    private var noteString: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isEnabled) {
                    isEnabled = false

                    requireActivity()
                        .findViewById<TextInputLayout>(R.id.input).visibility = View.VISIBLE
                    requireActivity()
                        .findViewById<Button>(R.id.save_note_btn).visibility = View.VISIBLE
                    requireActivity()
                        .findViewById<RecyclerView>(R.id.todolist_recycler_view).visibility =
                        View.VISIBLE

                    parentFragmentManager.popBackStack()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

        arguments?.let {
            noteId = it.getInt(ARG_PARAM_NOTE_ID)
            noteString = it.getString(ARG_PARAM_NOTE_STRING)
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEditNoteBinding {
        return FragmentEditNoteBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity()
            .findViewById<TextInputLayout>(R.id.input).visibility = View.GONE
        requireActivity()
            .findViewById<Button>(R.id.save_note_btn).visibility = View.GONE
        requireActivity()
            .findViewById<RecyclerView>(R.id.todolist_recycler_view).visibility =
            View.GONE

        binding.inputText.setText(noteString)

        binding.saveNoteBtn.setOnClickListener {

            if (binding.inputText.text.isNullOrEmpty()) {
                viewModel.deleteNoteById(noteId!!)

                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    R.string.note_deleted, Snackbar.LENGTH_SHORT
                )
                    .show()
            } else {
                viewModel.updateNoteById(noteId!!, binding.inputText.text.toString())

                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    R.string.note_updated, Snackbar.LENGTH_SHORT
                )
                    .show()
            }

            requireActivity()
                .findViewById<TextInputLayout>(R.id.input).visibility = View.VISIBLE
            requireActivity()
                .findViewById<Button>(R.id.save_note_btn).visibility = View.VISIBLE
            requireActivity()
                .findViewById<RecyclerView>(R.id.todolist_recycler_view).visibility =
                View.VISIBLE

            parentFragmentManager.popBackStack()
        }

        binding.cancelBtn.setOnClickListener {
            requireActivity()
                .findViewById<TextInputLayout>(R.id.input).visibility = View.VISIBLE
            requireActivity()
                .findViewById<Button>(R.id.save_note_btn).visibility = View.VISIBLE
            requireActivity()
                .findViewById<RecyclerView>(R.id.todolist_recycler_view).visibility =
                View.VISIBLE

            parentFragmentManager.popBackStack()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(noteId: Int, noteString: String) =
            EditNoteFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM_NOTE_ID, noteId)
                    putString(ARG_PARAM_NOTE_STRING, noteString)
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        callback.remove()
    }
}