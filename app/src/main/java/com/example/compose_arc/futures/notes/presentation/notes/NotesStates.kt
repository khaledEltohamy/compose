package com.example.compose_arc.futures.notes.presentation.notes

import com.example.compose_arc.futures.notes.domain.entity.Note
import com.example.compose_arc.futures.notes.domain.util.NoteOrder
import com.example.compose_arc.futures.notes.domain.util.OrderType


data class NotesStates (
    val  notes: List<Note> = emptyList() ,
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible : Boolean = false){
}