package com.example.todolist.ui.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.ToDoListApplication
import com.example.todolist.data.room.entity.ToDoListEntity
import com.example.todolist.databinding.FragmentNotesListBinding
import com.example.todolist.di.component.ActivityComponent
import com.example.todolist.ui.alertdialogs.DeleteNoteAlertDialog
import com.example.todolist.ui.recyclerview.NotesAdapter
import com.example.todolist.ui.recyclerview.RecyclerViewItemClickListener
import com.example.todolist.ui.viewmodel.NotesListVM
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import javax.inject.Inject

private const val ARG_DATE_PARAM = "date"
private const val ARG_BY_DATE_PARAM = "checkboxStatus"
private const val ARG_FINISHED_FLAG_PARAM = "flag"

class NotesListFragment : BaseFragment<FragmentNotesListBinding>(), RecyclerViewItemClickListener {

    @Inject
    lateinit var viewModel: NotesListVM

    private lateinit var activityComponent: ActivityComponent

    private var dateParam: String? = null
    private var byDate: Boolean? = null
    private var finished: Boolean? = null

    private lateinit var adapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            dateParam = it.getString(ARG_DATE_PARAM)
            byDate = it.getBoolean(ARG_BY_DATE_PARAM)
            finished = it.getBoolean(ARG_FINISHED_FLAG_PARAM)
        }
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNotesListBinding {
        return FragmentNotesListBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appComponent = (requireActivity().application as ToDoListApplication).getAppComponent()
        activityComponent = appComponent.activityComponent().create()
        activityComponent.injectNotesListFragment(this)

        if (byDate == true) {
            viewModel.getListOfNotesByDate(dateParam!!)
                .observe(this.viewLifecycleOwner) { notesList ->
                    createUpdateRecyclerView(notesList)
                }
        } else {
            if (finished == true) {
                viewModel.showAllFinishedTasks().observe(this.viewLifecycleOwner) { notesList ->
                    createUpdateRecyclerView(notesList)
                    if (notesList.isNotEmpty()) {
                        binding.deleteFloatBtn.visibility = View.VISIBLE
                    } else {
                        showSnackbar(getString(R.string.no_finished_tasks))
                    }
                }
            } else {
                viewModel.showAllUnfinishedTasks().observe(this.viewLifecycleOwner) { notesList ->
                    createUpdateRecyclerView(notesList)
                    if (notesList.isEmpty()) {
                        showSnackbar(getString(R.string.all_work_is_done))
                    }
                }
            }
        }

        binding.deleteFloatBtn.setOnClickListener {
            viewModel.deleteAllFinishedTasks()
            binding.deleteFloatBtn.visibility = View.GONE
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(
            requireActivity().findViewById(android.R.id.content),
            message, Snackbar.LENGTH_SHORT
        )
            .show()
    }

    private fun createUpdateRecyclerView(notesList: List<ToDoListEntity>) {
        adapter = NotesAdapter(this)
        adapter.submitList(notesList)
        binding.todolistRecyclerView.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance(dateParam: String, byDate: Boolean, finished: Boolean) =
            NotesListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_DATE_PARAM, dateParam)
                    putBoolean(ARG_BY_DATE_PARAM, byDate)
                    putBoolean(ARG_FINISHED_FLAG_PARAM, finished)
                }
            }
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
                        DeleteNoteAlertDialog.newInstance(noteId)
                            .show(parentFragmentManager, "DELETE_SET")
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
}