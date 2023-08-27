package com.example.compose_arc.futures.notes.presentation.addEditNotes

import androidx.lifecycle.ViewModel
import com.example.compose_arc.futures.notes.domain.usecase.NoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(private val useCase: NoteUseCase) : ViewModel(){

}