package com.example.presentation.noteslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.Note
import com.example.presentation.databinding.NoteItemBinding

//TODO item style move into styles?
interface RecyclerViewItemClickListener {
    fun onMenuClick(menuItem: View, noteId: Int, noteText: String)
    fun checkboxClick(noteId: Int, checkboxStatus: Boolean)
}

class NotesAdapter(private val itemClickListener: RecyclerViewItemClickListener) :
    ListAdapter<Note, NotesAdapter.NotesViewHolder>(ToDoListDiffCallback()) {

    inner class NotesViewHolder(binding: NoteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var checkBox: CheckBox = binding.checkbox
        private val dateText: TextView = binding.dateText
        private val noteText: TextView = binding.noteText
        private val menuBtn: FrameLayout = binding.menuOptionsBtn
        var noteId: Int = -1

        init {
            menuBtn.setOnClickListener {
                itemClickListener.onMenuClick(it, noteId, noteText.text.toString())
            }

            checkBox.setOnClickListener {
                itemClickListener.checkboxClick(noteId, checkBox.isChecked)
            }
        }

        fun bind(note: Note) {
            checkBox.isChecked = note.checkboxStatus
            dateText.text = note.date
            noteText.text = note.text
            noteId = note.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val view = NoteItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}