package com.example.compose_arc.futures.notes.presentation.notes.screen

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.compose_arc.core.Screen

import com.example.compose_arc.futures.notes.presentation.notes.NoteEvent
import com.example.compose_arc.futures.notes.presentation.notes.NotesViewModel
import com.example.compose_arc.futures.notes.presentation.notes.components.NoteItem
import com.example.compose_arc.futures.notes.presentation.notes.components.OrderSection
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DefaultNoteScreen (navController: NavController , viewModel: NotesViewModel = hiltViewModel()) {
    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
         snackbarHost = {SnackbarHost(snackbarHostState)},
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screen.AddEditScreen.route)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add note")
            }
        }) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Your note", style = MaterialTheme.typography.bodyLarge)
                IconButton(onClick = { viewModel.onEvent(NoteEvent.ToggleOrderSection) }) {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = "Sort")
                }
            }
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    onOrderChange = {
                        viewModel.onEvent(NoteEvent.Order(it))
                    },
                    noteOrder = state.noteOrder
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()){
                items(state.notes){
                     note -> NoteItem(note = note , modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navController.navigate(
                            Screen.AddEditScreen.route +
                                    "?noteId=${note.id}&noteColor=${note.color}"
                        )
                    } , onDeleteItem = {
                         viewModel.onEvent(NoteEvent.DeleteNote(note))
                    scope.launch {
                     val result =   snackbarHostState.showSnackbar(message = "Note deleted" , actionLabel = "Undo")
                     if (result == SnackbarResult.ActionPerformed){
                         viewModel.onEvent(NoteEvent.RestoreNote)
                     }
                    }
                })
                  Spacer(modifier = Modifier.height(16.dp))

                }
            }
        }


    }
}