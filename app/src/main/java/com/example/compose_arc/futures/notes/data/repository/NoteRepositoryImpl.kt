package com.example.compose_arc.futures.notes.data.repository

import com.example.compose_arc.futures.notes.data.datasource.NoteDoa
import com.example.compose_arc.futures.notes.domain.entity.Note
import com.example.compose_arc.futures.notes.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(private  val dao: NoteDoa) : NoteRepository{

    override fun getNotes(): Flow<List<Note>> {
      return  dao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
     return  dao.getNoteById(id)
    }

    override suspend fun insertNote(note: Note) {
        dao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }
}