package com.example.compose_arc.di

import android.app.Application
import androidx.room.Room
import com.example.compose_arc.futures.notes.data.datasource.NoteDatabase
import com.example.compose_arc.futures.notes.data.repository.NoteRepositoryImpl
import com.example.compose_arc.futures.notes.domain.repository.NoteRepository
import com.example.compose_arc.futures.notes.domain.usecase.AddNote
import com.example.compose_arc.futures.notes.domain.usecase.DeleteNote
import com.example.compose_arc.futures.notes.domain.usecase.GetNotes
import com.example.compose_arc.futures.notes.domain.usecase.NoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun providerNoteDataBase(app:Application) : NoteDatabase {
        return  Room.databaseBuilder(
            app ,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providerNoteRepository(db:NoteDatabase): NoteRepository {
        return  NoteRepositoryImpl(db.noteDoa)
    }

    @Provides
    @Singleton
    fun providerNoteUseCase(repository: NoteRepository): NoteUseCase {
        return  NoteUseCase(
            getNotes =  GetNotes(repository),
            deleteNote =  DeleteNote(repository),
            addNote = AddNote(repository)
        )
    }
}