package com.example.presentation.edit

//import android.R
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Button
//import androidx.activity.OnBackPressedCallback
//import androidx.fragment.app.Fragment
//import androidx.recyclerview.widget.RecyclerView
//import com.example.todolist.databinding.FragmentEditNoteBinding
//import com.example.todolist.ui.viewmodel.EditDeleteNoteVM
//import com.google.android.material.snackbar.Snackbar
//import com.google.android.material.textfield.TextInputLayout
//import javax.inject.Inject

private const val ARG_PARAM_NOTE_ID = "noteId"
private const val ARG_PARAM_NOTE_STRING = "noteString"

//class EditNoteFragment : BaseFragment<FragmentEditNoteBinding>() {
//
//    @Inject
//    lateinit var viewModel: EditDeleteNoteVM
//
//    private lateinit var callback: OnBackPressedCallback
//
//    private var noteId: Int? = null
//    private var noteString: String? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        callback = object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                if (isEnabled) {
//                    isEnabled = false
//
//                    Fragment.requireActivity()
//                        .findViewById<TextInputLayout>(R.id.input).visibility = View.VISIBLE
//                    Fragment.requireActivity()
//                        .findViewById<Button>(R.id.save_note_btn).visibility = View.VISIBLE
//                    Fragment.requireActivity()
//                        .findViewById<RecyclerView>(R.id.todolist_recycler_view).visibility =
//                        View.VISIBLE
//
//                    Fragment.getParentFragmentManager.popBackStack()
//                }
//            }
//        }
//        Fragment.requireActivity().onBackPressedDispatcher.addCallback(this, callback)
//
//        Fragment.getArguments?.let {
//            noteId = it.getInt(ARG_PARAM_NOTE_ID)
//            noteString = it.getString(ARG_PARAM_NOTE_STRING)
//        }
//    }
//
//    override fun getViewBinding(
//        inflater: LayoutInflater,
//        container: ViewGroup?
//    ): FragmentEditNoteBinding {
//        return FragmentEditNoteBinding.inflate(inflater, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        Fragment.requireActivity()
//            .findViewById<TextInputLayout>(R.id.input).visibility = View.GONE
//        Fragment.requireActivity()
//            .findViewById<Button>(R.id.save_note_btn).visibility = View.GONE
//        Fragment.requireActivity()
//            .findViewById<RecyclerView>(R.id.todolist_recycler_view).visibility =
//            View.GONE
//
//        binding.inputText.setText(noteString)
//
//        binding.saveNoteBtn.setOnClickListener {
//
//            if (binding.inputText.text.isNullOrEmpty()) {
//                viewModel.deleteNoteById(noteId!!)
//                showSnackbar(Fragment.getString(R.string.note_deleted))
//            } else {
//                viewModel.updateNoteById(noteId!!, binding.inputText.text.toString())
//            }
//
//            Fragment.requireActivity()
//                .findViewById<TextInputLayout>(R.id.input).visibility = View.VISIBLE
//            Fragment.requireActivity()
//                .findViewById<Button>(R.id.save_note_btn).visibility = View.VISIBLE
//            Fragment.requireActivity()
//                .findViewById<RecyclerView>(R.id.todolist_recycler_view).visibility =
//                View.VISIBLE
//
//            Fragment.getParentFragmentManager.popBackStack()
//        }
//
//        binding.cancelBtn.setOnClickListener {
//            Fragment.requireActivity()
//                .findViewById<TextInputLayout>(R.id.input).visibility = View.VISIBLE
//            Fragment.requireActivity()
//                .findViewById<Button>(R.id.save_note_btn).visibility = View.VISIBLE
//            Fragment.requireActivity()
//                .findViewById<RecyclerView>(R.id.todolist_recycler_view).visibility =
//                View.VISIBLE
//
//            Fragment.getParentFragmentManager.popBackStack()
//        }
//    }
//
//    private fun showSnackbar(message: String) {
//        Snackbar.make(
//            Fragment.requireActivity().findViewById(R.id.content),
//            message, Snackbar.LENGTH_SHORT
//        ).show()
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        callback.remove()
//    }
//
//    companion object {
//        @JvmStatic
//        fun newInstance(noteId: Int, noteString: String) =
//            EditNoteFragment().apply {
//                Fragment.setArguments = Bundle().apply {
//                    putInt(ARG_PARAM_NOTE_ID, noteId)
//                    putString(ARG_PARAM_NOTE_STRING, noteString)
//                }
//            }
//    }
//}