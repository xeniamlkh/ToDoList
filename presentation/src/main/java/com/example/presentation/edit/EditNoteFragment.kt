package com.example.presentation.edit

import com.example.presentation.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.presentation.BaseFragment
import com.example.presentation.databinding.FragmentEditNoteBinding
import com.example.presentation.noteslist.NotesVM
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_PARAM_NOTE_ID = "noteId"
private const val ARG_PARAM_NOTE_STRING = "noteString"

@AndroidEntryPoint
class EditNoteFragment : BaseFragment<FragmentEditNoteBinding>() {

    private val viewModel: NotesVM by activityViewModels()

    //TODO Check OnBackPressedCallback
    private lateinit var callback: OnBackPressedCallback

    private val noteId: Int by lazy {
        requireArguments().getInt(ARG_PARAM_NOTE_ID)
    }
    private val noteString: String by lazy {
        requireArguments().getString(ARG_PARAM_NOTE_STRING) ?: ""
    }

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

                    getParentFragmentManager().popBackStack()
                }
            }
        }
        requireActivity().onBackPressedDispatcher
            .addCallback(this, callback)
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEditNoteBinding {
        return FragmentEditNoteBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO Check views' visibility - why if return to the previous screen?
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
                viewModel.deleteNoteById(noteId)
                showSnackBar(getString(R.string.note_deleted))
            } else {
                viewModel.updateNoteById(noteId, binding.inputText.text.toString())
            }

            requireActivity()
                .findViewById<TextInputLayout>(R.id.input).visibility = View.VISIBLE
            requireActivity()
                .findViewById<Button>(R.id.save_note_btn).visibility = View.VISIBLE
            requireActivity()
                .findViewById<RecyclerView>(R.id.todolist_recycler_view).visibility =
                View.VISIBLE

            getParentFragmentManager().popBackStack()
        }

        binding.cancelBtn.setOnClickListener {
            requireActivity()
                .findViewById<TextInputLayout>(R.id.input).visibility = View.VISIBLE
            requireActivity()
                .findViewById<Button>(R.id.save_note_btn).visibility = View.VISIBLE
            requireActivity()
                .findViewById<RecyclerView>(R.id.todolist_recycler_view).visibility =
                View.VISIBLE

            getParentFragmentManager().popBackStack()
        }
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(
            requireActivity().findViewById(android.R.id.content),
            message, Snackbar.LENGTH_SHORT
        ).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        callback.remove()
    }

    companion object {
        fun newInstance(noteId: Int, noteString: String) =
            EditNoteFragment().apply {
                arguments = bundleOf(
                    ARG_PARAM_NOTE_ID to noteId,
                    ARG_PARAM_NOTE_STRING to noteString
                )
            }
    }
}