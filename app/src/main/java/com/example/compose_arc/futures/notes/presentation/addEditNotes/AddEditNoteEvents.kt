package com.example.compose_arc.futures.notes.presentation.addEditNotes

import androidx.compose.ui.focus.FocusState

sealed class AddEditNoteEvents {
    data class EnterTitleNote(val title:String): AddEditNoteEvents()
    data class ChangeTitleFocus(val focus:FocusState): AddEditNoteEvents()
    data class EnterContentNote(val title:String): AddEditNoteEvents()
    data class ChangeContentFocus(val focus:FocusState): AddEditNoteEvents()

    data class  ChangeColorNote(val color : Int):AddEditNoteEvents()

    object  SaveNote:AddEditNoteEvents()



}
