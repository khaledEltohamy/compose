package com.example.compose_arc.futures.notes.presentation.addEditNotes.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun TransparentHintTextField(
    modifier: Modifier,
    text:String,
    hint : String ,
    isHiltVisible:Boolean = true,
    onValueChange : (String)->Unit,
    textStyle: TextStyle = TextStyle(),
    singleLine:Boolean = false,
    onFocusChange:(FocusState)->Unit
){
    Box (modifier = modifier) {
         BasicTextField(
             value = text,
             onValueChange = onValueChange ,
             textStyle = textStyle,
             modifier = Modifier.border(width = 1.dp , Color.Gray)
                 .fillMaxWidth()
                 .onFocusChanged {
                     onFocusChange(it)
                 },
             
             )
        if (isHiltVisible){
            Text(text = hint , style = textStyle , color = Color.DarkGray)
        }
    }
}


