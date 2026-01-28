package com.example.domain.models

data class Note(
    val id: Int,
    val date: String,
    val checkboxStatus: Boolean,
    val text: String
)