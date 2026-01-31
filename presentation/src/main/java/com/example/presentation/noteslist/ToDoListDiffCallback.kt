package com.example.presentation.noteslist

import androidx.recyclerview.widget.DiffUtil
import com.example.domain.models.Note

class ToDoListDiffCallback: DiffUtil.ItemCallback<Note>() {

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
       return  oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return  oldItem.id == newItem.id
    }
}