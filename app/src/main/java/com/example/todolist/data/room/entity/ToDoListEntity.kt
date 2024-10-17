package com.example.todolist.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
data class ToDoListEntity(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo val date: String,
    @ColumnInfo(name = "checkbox status") val checkboxStatus: Boolean,
    @ColumnInfo val note: String
)