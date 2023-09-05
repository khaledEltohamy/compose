package com.example.compose_arc.futures.notes.presentation.addEditNotes

data class NoteTextFieldState(
    val text : String = "",
    val hint :String = "",
    val isHintVisible:Boolean = true
)
