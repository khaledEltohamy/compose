package com.example.compose_arc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.compose_arc.core.Screen
import com.example.compose_arc.futures.notes.domain.entity.Note
import com.example.compose_arc.futures.notes.presentation.addEditNotes.AddEditNoteScreen
import com.example.compose_arc.futures.notes.presentation.notes.screen.DefaultNoteScreen
import com.example.compose_arc.ui.theme.Compose_arcTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            Compose_arcTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                   NavHost(navController = navController, startDestination = Screen.NotesScreen.route,
                        ){
                       composable(route = Screen.NotesScreen.route){
                           DefaultNoteScreen(navController = navController)
                       }
                       composable(
                           route = Screen.AddEditScreen.route +
                                   "?noteId={noteId}&noteColor={noteColor}",
                           arguments = listOf(
                               navArgument(
                                   name = "noteId"
                               ) {
                                   type = NavType.IntType
                                   defaultValue = -1
                               },
                               navArgument(
                                   name = "noteColor"
                               ) {
                                   type = NavType.IntType
                                   defaultValue = -1
                               },
                           )
                       ) {
                           val color = it.arguments?.getInt("noteColor") ?: -1
                           AddEditNoteScreen(
                               navController = navController,
                               noteColor = color
                           )
                       }
                   }

                }
            }
        }
    }
}



