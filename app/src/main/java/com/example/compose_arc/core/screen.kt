package com.example.compose_arc.core

sealed class Screen(val route : String){
    object  NotesScreen:Screen("notesScreen")
    object  AddEditScreen:Screen("add_edit_screen")

}
