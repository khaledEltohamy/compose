package com.example.compose_arc.futures.notes.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.compose_arc.futures.notes.domain.entity.Note

@Database(
    entities = [Note::class],
    version = 1
)
abstract class NoteDatabase : RoomDatabase() {
    abstract val noteDoa:NoteDoa
    companion object {
        const val DATABASE_NAME = "notes.db"
    }
}