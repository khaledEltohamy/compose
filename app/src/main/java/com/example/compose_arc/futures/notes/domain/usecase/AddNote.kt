package com.example.compose_arc.futures.notes.domain.usecase

import com.example.compose_arc.futures.notes.domain.entity.InvalidNoteException
import com.example.compose_arc.futures.notes.domain.entity.Note
import com.example.compose_arc.futures.notes.domain.repository.NoteRepository

class AddNote (private  val repository: NoteRepository) {
    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note){
         if (note.name.isBlank()){
            throw  InvalidNoteException("Note title can't be blank.")
         }
         if (note.content.isBlank()){
             throw InvalidNoteException("The note content can't be blank.")
         }
        repository.insertNote(note)
    }
}