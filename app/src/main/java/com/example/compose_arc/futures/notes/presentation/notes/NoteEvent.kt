package com.example.compose_arc.futures.notes.presentation.notes

import com.example.compose_arc.futures.notes.domain.entity.Note
import com.example.compose_arc.futures.notes.domain.util.NoteOrder

sealed class NoteEvent {
    data class  Order(val noteOrder: NoteOrder): NoteEvent()
    data class  DeleteNote(val note: Note) : NoteEvent()
    object RestoreNote:NoteEvent()
    object ToggleOrderSection: NoteEvent()
}
