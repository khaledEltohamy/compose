package com.example.compose_arc.futures.notes.domain.usecase

import com.example.compose_arc.futures.notes.domain.entity.Note
import com.example.compose_arc.futures.notes.domain.repository.NoteRepository
import com.example.compose_arc.futures.notes.domain.util.NoteOrder
import com.example.compose_arc.futures.notes.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotes (
    private val repository: NoteRepository
){
    operator fun invoke(noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)):Flow<List<Note>> {
       return repository.getNotes().map { notes ->
           when (noteOrder.order){
               is OrderType.Ascending -> {
                   when (noteOrder){
                       is NoteOrder.Title -> notes.sortedBy { it.name.lowercase() }
                       is NoteOrder.Date ->notes.sortedBy { it.timestamp }
                       is NoteOrder.Color ->notes.sortedBy { it.color }

                   }
               }
               is OrderType.Descending -> {
                   when (noteOrder){
                       is NoteOrder.Title -> notes.sortedByDescending { it.name.lowercase() }
                       is NoteOrder.Date ->notes.sortedByDescending { it.timestamp }
                       is NoteOrder.Color ->notes.sortedByDescending { it.color }
                   }
               }
           }

       }
    }

}