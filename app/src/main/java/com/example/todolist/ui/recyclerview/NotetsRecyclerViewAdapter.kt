package com.example.todolist.ui.recyclerview

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.NoteItemBinding
import com.example.todolist.model.data.ToDoListEntity

interface RecyclerViewItemClickListener {
    fun onMenuClick(menuItem: View, noteId: Int, noteText: String)
    fun checkboxClick(noteId: Int, checkboxStatus: Boolean)
}

class NotesRecyclerViewAdapter(
    private val notes: List<ToDoListEntity>,
    private val itemClickListener: RecyclerViewItemClickListener
) :
    RecyclerView.Adapter<NotesRecyclerViewAdapter.NotesViewHolder>() {

    inner class NotesViewHolder(binding: NoteItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val checkBox: CheckBox
        val noteText: TextView
        val menuBtn: FrameLayout
        var noteId: Int = -1

        init {
            checkBox = binding.checkbox
            noteText = binding.noteText
            menuBtn = binding.menuOptionsBtn

            menuBtn.setOnClickListener {
                if (adapterPosition >= 0) {
                    itemClickListener.onMenuClick(it, noteId, noteText.text.toString())
                }
            }

            checkBox.setOnClickListener {
                if (adapterPosition >= 0) {
                    itemClickListener
                        .checkboxClick(noteId, checkBox.isChecked)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val noteItem = notes[position]
        holder.checkBox.isChecked = noteItem.checkboxStatus
        holder.noteText.text = noteItem.note
        holder.noteId = noteItem.id

        if (holder.checkBox.isChecked){
            holder.noteText.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        }
    }
}