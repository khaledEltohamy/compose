package com.example.compose_arc.futures.notes.presentation.addEditNotes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose_arc.futures.notes.domain.entity.InvalidNoteException
import com.example.compose_arc.futures.notes.domain.entity.Note
import com.example.compose_arc.futures.notes.domain.usecase.NoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(private val useCase: NoteUseCase , savedStateHandle: SavedStateHandle) : ViewModel(){
   private  val _noteTitle = mutableStateOf(NoteTextFieldState(
       hint = "Enter your title ..."
   ))
    val noteTitle: State<NoteTextFieldState> = _noteTitle

    private  val _noteContent = mutableStateOf(NoteTextFieldState(
        hint = "Enter your Content"
    ))
    val noteContent: State<NoteTextFieldState> = _noteContent

    private  val _noteColor = mutableIntStateOf(Note.noteColors.random().toArgb())
    val noteColor: State<Int> = _noteColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()
    private var currentIdNote : Int ?  = null

    init {
        savedStateHandle.get<Int>("noteId")?.let{
            noteId-> if(noteId != -1){
                viewModelScope.launch {
                    useCase.getNote(noteId)?.also {
                            note -> currentIdNote = note.id
                        _noteTitle.value = noteTitle.value.copy(
                            text = note.name,
                            isHintVisible = false
                             )
                        _noteContent.value = noteContent.value.copy(
                            text = note.content,
                            isHintVisible = false
                        )
                        _noteColor.value = note.color
                    }
                }

        }
        }
    }
    fun  onEvent(event : AddEditNoteEvents){
         when(event){
             is AddEditNoteEvents.ChangeColorNote ->{
                 _noteColor.value = event.color
             }

             is AddEditNoteEvents.EnterContentNote -> {
                 _noteContent.value = noteContent.value.copy(
                     text = event.title
                 )
             }
             is AddEditNoteEvents.ChangeContentFocus -> {
                 _noteContent.value = noteContent.value.copy(
                     isHintVisible = !event.focus.isFocused && noteContent.value.text.isBlank())
             }


             is AddEditNoteEvents.EnterTitleNote ->{
                 _noteTitle.value = noteTitle.value.copy(
                     text = event.title
                 )
             }
             is AddEditNoteEvents.ChangeTitleFocus -> {
                 _noteTitle.value = noteTitle.value.copy(
                     isHintVisible = !event.focus.isFocused && noteTitle.value.text.isBlank()
                 )
             }
             AddEditNoteEvents.SaveNote -> {
                 viewModelScope.launch {
                     try {
                     useCase.addNote(
                         Note(name = noteTitle.value.text,
                             content = noteContent.value.text,
                             timestamp = System.currentTimeMillis(),
                             color = noteColor.value,
                             id = currentIdNote
                         )
                     )
                         _eventFlow.emit(UiEvent.saveNote)
                     }catch (e: InvalidNoteException){
                         _eventFlow.emit(UiEvent.showSnackBar( massage = e.message ?: "Can't save note"
                         ))

                     }
                 }
             }
         }
    }
  sealed class  UiEvent {
      data class  showSnackBar (val massage:String):UiEvent()
      object saveNote : UiEvent()
  }
}