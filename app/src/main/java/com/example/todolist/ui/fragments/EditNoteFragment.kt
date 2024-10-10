package com.example.todolist.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
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
import com.example.todolist.ui.viewmodel.ToDoListViewModel
import com.example.todolist.ui.viewmodel.ToDoListViewModelFactory
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout

private const val ARG_PARAM_NOTE_ID = "noteId"
private const val ARG_PARAM_NOTE_STRING = "noteString"

class EditNoteFragment : Fragment() {

    private var _binding: FragmentEditNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var callback: OnBackPressedCallback

    private val viewModel: ToDoListViewModel by activityViewModels {
        ToDoListViewModelFactory(
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditNoteBinding.inflate(inflater, container, false)
        return binding.root
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        callback.remove()
    }
}