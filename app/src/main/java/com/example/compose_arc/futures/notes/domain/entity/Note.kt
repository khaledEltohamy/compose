package com.example.compose_arc.futures.notes.domain.entity
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.compose_arc.ui.theme.*

@Entity
data class Note(
    val name:String ,
    val content:String ,
    val timestamp:Long ,
    val color : Int ,
    @PrimaryKey val id: Int? = null
    ){
     companion object { val noteColors = listOf(Purple80 ,Pink40 ,PurpleGrey40) }


}

class InvalidNoteException (message: String): Exception(message)