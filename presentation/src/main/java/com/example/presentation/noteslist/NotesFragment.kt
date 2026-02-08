package com.example.presentation.noteslist

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.enums.SortCriteria
import com.example.domain.models.Note
import com.example.presentation.BaseFragment
import com.example.presentation.R
import com.example.presentation.databinding.FragmentNotesListBinding
import com.example.presentation.edit.DeleteNoteAlertDialog
import com.example.presentation.edit.EditNoteFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint

private const val ARG_SORT_PARAM = "sortCriteria"
private const val ARG_DATE_PARAM = "date"

@AndroidEntryPoint
class NotesListFragment : BaseFragment<FragmentNotesListBinding>(), RecyclerViewItemClickListener {

    private val viewModel: NotesVM by activityViewModels()

    private val sortCriteria: SortCriteria by lazy {
        SortCriteria.valueOf(
            requireArguments().getString(ARG_SORT_PARAM) ?: ""
        )
    }

    private val dateParam: String by lazy {
        requireArguments().getString(ARG_DATE_PARAM) ?: ""
    }

    private lateinit var adapter: NotesAdapter

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNotesListBinding {
        return FragmentNotesListBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (sortCriteria) {
            SortCriteria.DATE -> {
                viewModel.getListOfNotesByDate(dateParam)
                    .observe(this.viewLifecycleOwner) { notesList ->
                        createUpdateRecyclerView(notesList)
                    }
            }

            SortCriteria.COMPLETED -> {
                viewModel.showAllFinishedTasks().observe(this.viewLifecycleOwner) { notesList ->
                    createUpdateRecyclerView(notesList)
                    if (notesList.isNotEmpty()) {
                        binding.deleteFloatBtn.visibility = View.VISIBLE
                    } else {
                        showSnackBar(R.string.no_finished_tasks)
                    }
                }
            }

            SortCriteria.UNFINISHED -> {
                viewModel.showAllUnfinishedTasks().observe(this.viewLifecycleOwner) { notesList ->
                    createUpdateRecyclerView(notesList)
                    if (notesList.isEmpty()) {
                        showSnackBar(R.string.all_work_is_done)
                    }
                }
            }
        }

        binding.deleteFloatBtn.setOnClickListener {
            viewModel.deleteAllFinishedTasks()
            binding.deleteFloatBtn.visibility = View.GONE
        }
    }

    private fun showSnackBar(stringId: Int) {
        Snackbar.make(
            requireActivity().findViewById(android.R.id.content),
            getString(stringId), Snackbar.LENGTH_SHORT
        )
            .show()
    }

    private fun createUpdateRecyclerView(notesList: List<Note>) {
        adapter = NotesAdapter(this)
        adapter.submitList(notesList)
        binding.todolistRecyclerView.adapter = adapter
    }

    override fun onMenuClick(menuItem: View, noteId: Int, noteText: String) {
        val popupMenu = PopupMenu(requireContext(), menuItem)
        popupMenu.inflate(R.menu.menu)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            popupMenu.setForceShowIcon(true)
        }

        popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when (item?.itemId) {
                    R.id.delete_btn -> {
                        viewModel.setNoteIdToDelete(noteId)
                        DeleteNoteAlertDialog().show(parentFragmentManager, "DELETE_NOTE")
                        return true
                    }

                    R.id.edit_btn -> {
                        requireActivity()
                            .findViewById<TextInputLayout>(R.id.input).visibility = View.GONE
                        requireActivity()
                            .findViewById<Button>(R.id.save_note_btn).visibility = View.GONE
                        requireActivity()
                            .findViewById<RecyclerView>(R.id.todolist_recycler_view).visibility =
                            View.GONE

                        childFragmentManager.beginTransaction()
                            .replace(
                                R.id.edit_fragment_container,
                                EditNoteFragment.newInstance(noteId, noteText)
                            )
                            .addToBackStack(null)
                            .commit()

                        return true
                    }

                    else -> {
                        return false
                    }
                }
            }
        })
        popupMenu.show()
    }

    override fun checkboxClick(noteId: Int, checkboxStatus: Boolean) {
        viewModel.updateNoteStatus(noteId, checkboxStatus)
    }

    companion object {
        fun newInstance(
            sortCriteria: String,
            date: String?
        ) =
            NotesListFragment().apply {
                arguments = bundleOf(
                    ARG_SORT_PARAM to sortCriteria,
                    ARG_DATE_PARAM to date
                )
            }
    }
}