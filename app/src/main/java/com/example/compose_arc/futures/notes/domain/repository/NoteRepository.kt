package com.example.compose_arc.futures.notes.domain.repository

import com.example.compose_arc.futures.notes.domain.entity.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotes():Flow<List<Note>>
    suspend fun getNoteById(id:Int):Note?
    suspend fun insertNote(note: Note)
    suspend fun deleteNote(note: Note)
}