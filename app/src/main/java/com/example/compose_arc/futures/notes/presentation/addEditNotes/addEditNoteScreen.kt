package com.example.compose_arc.futures.notes.presentation.addEditNotes

import android.annotation.SuppressLint
import androidx.compose.animation.Animatable
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.compose_arc.futures.notes.domain.entity.Note
import com.example.compose_arc.futures.notes.presentation.addEditNotes.components.TransparentHintTextField
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
@ExperimentalAnimationApi
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")

fun AddEditNoteScreen(
    navController: NavController,
    noteColor: Int,
    viewModel: AddEditNoteViewModel = hiltViewModel()
){
  val titleState = viewModel.noteTitle.value
  val contentState = viewModel.noteContent.value
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val noteBackgroundAnimatable = remember {
        Animatable(
            Color(if (noteColor != -1) noteColor else viewModel.noteColor.value)
        )
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is AddEditNoteViewModel.UiEvent.showSnackBar -> {
                    snackBarHostState.showSnackbar(message = event.massage)

                }
                is AddEditNoteViewModel.UiEvent.saveNote -> {
                    navController.navigateUp()
                }

            }
        }
    }

    Scaffold (

        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(AddEditNoteEvents.SaveNote) } ,
            ) {
                Icon(imageVector = Icons.Default.Star, contentDescription = "save" )
            }
        },
        snackbarHost = { SnackbarHost(snackBarHostState) },

    )


    {
      Column(
          modifier = Modifier
              .fillMaxSize()
              .background(noteBackgroundAnimatable.value)
              .padding(16.dp)
      ) {
         Row (modifier = Modifier
             .fillMaxWidth()
             .padding(8.dp),
              horizontalArrangement = Arrangement.SpaceBetween){
             Note.noteColors.forEach{color ->
                 val colorInt = color.toArgb()
                 Box (
                     modifier = Modifier
                         .size(50.dp)
                         .shadow(15.dp, CircleShape)
                         .clip(CircleShape)
                         .background(color)
                         .border(
                             width = 3.dp, color = if (viewModel.noteColor.value == colorInt) {
                                 Color.Black
                             } else {
                                 Color.Transparent
                             },
                             shape = CircleShape
                         )
                         .clickable {
                             scope.launch {
                                 noteBackgroundAnimatable.animateTo(
                                     targetValue = Color(colorInt),
                                     animationSpec = tween(durationMillis = 500)
                                 )
                             }
                             viewModel.onEvent(AddEditNoteEvents.ChangeColorNote(color = colorInt))
                         })
             }

         }
          Spacer(modifier = Modifier.height(16.dp))
          TransparentHintTextField(
              modifier = Modifier.fillMaxWidth(),
              text = titleState.text,
              hint = titleState.text,
              isHiltVisible = titleState.isHintVisible,
              singleLine = true,
              textStyle = MaterialTheme.typography.bodyMedium,
              onValueChange = {
                              viewModel.onEvent(AddEditNoteEvents.EnterTitleNote(it))
              },
              onFocusChange = {
                  viewModel.onEvent(AddEditNoteEvents.ChangeTitleFocus(it))
              }

          )
          Spacer(modifier = Modifier.height(16.dp))
          TransparentHintTextField(
              modifier = Modifier.fillMaxHeight(),
              text = contentState.text,
              hint = contentState.text,
              isHiltVisible = contentState.isHintVisible,
              singleLine = true,
              textStyle = MaterialTheme.typography.bodySmall,
              onValueChange = {
                  viewModel.onEvent(AddEditNoteEvents.EnterContentNote(it))
              },
              onFocusChange = {
                  viewModel.onEvent(AddEditNoteEvents.ChangeContentFocus(it))
              }

          )
      }
    }
}