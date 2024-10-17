package com.example.todolist.ui.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.example.todolist.data.room.entity.ToDoListEntity

class ToDoListDiffCallback: DiffUtil.ItemCallback<ToDoListEntity>() {

    override fun areContentsTheSame(oldItem: ToDoListEntity, newItem: ToDoListEntity): Boolean {
       return  oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: ToDoListEntity, newItem: ToDoListEntity): Boolean {
        return  oldItem.id == newItem.id
    }
}