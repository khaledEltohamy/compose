package com.example.compose_arc.futures.notes.domain.usecase

import com.example.compose_arc.futures.notes.domain.entity.Note
import com.example.compose_arc.futures.notes.domain.repository.NoteRepository

class DeleteNote (private  val repository: NoteRepository) {
    suspend operator fun invoke (note: Note){
        repository.deleteNote(note)
    }
}