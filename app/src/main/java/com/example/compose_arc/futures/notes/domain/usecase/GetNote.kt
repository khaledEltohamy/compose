package com.example.compose_arc.futures.notes.domain.usecase

import com.example.compose_arc.futures.notes.domain.entity.Note
import com.example.compose_arc.futures.notes.domain.repository.NoteRepository

class GetNote (private val repository: NoteRepository){
    suspend operator fun invoke(id:Int) : Note?{
       return repository.getNoteById(id)
    }
}