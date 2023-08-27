package com.example.compose_arc.futures.notes.presentation.notes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose_arc.futures.notes.domain.entity.Note
import com.example.compose_arc.futures.notes.domain.usecase.NoteUseCase
import com.example.compose_arc.futures.notes.domain.util.NoteOrder
import com.example.compose_arc.futures.notes.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(private val noteUseCase:NoteUseCase) : ViewModel(){
    private val _state = mutableStateOf(NotesStates())
    val state: State<NotesStates> = _state
    private  var recentlyDeletedNote : Note? = null
    private var getNotesJob: Job? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent (event: NoteEvent){
      when(event){
          is NoteEvent.Order -> {
              if (state.value.noteOrder::class.java == event.noteOrder::class && state.value.noteOrder.order == event.noteOrder.order){
                  noteUseCase.getNotes.invoke(event.noteOrder)
              }
          }
          is NoteEvent.DeleteNote -> {
              viewModelScope.launch {
                  noteUseCase.deleteNote(event.note)
                  recentlyDeletedNote = event.note
              }
          }
          is NoteEvent.RestoreNote ->{
              viewModelScope.launch {
                  noteUseCase.addNote(recentlyDeletedNote ?: return@launch)
                  recentlyDeletedNote = null
               }
          }
          is NoteEvent.ToggleOrderSection ->{
              _state.value = state.value.copy( isOrderSectionVisible = !state.value.isOrderSectionVisible )
          }
      }
  }


    private fun getNotes(order: NoteOrder){
        getNotesJob?.cancel()
       getNotesJob = noteUseCase.getNotes(order).onEach { notes ->
            _state.value = state.value.copy(
                noteOrder = order,
                notes = notes,
            )
        }.launchIn(viewModelScope)
    }
}