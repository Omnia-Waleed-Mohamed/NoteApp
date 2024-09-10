package com.example.noteapp.data.dp

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.noteapp.ui.theme.BabyBlue
import com.example.noteapp.ui.theme.BabyBlue1
import com.example.noteapp.ui.theme.Blue
import com.example.noteapp.ui.theme.Green
import com.example.noteapp.ui.theme.Orang
import com.example.noteapp.ui.theme.Pink40
import com.example.noteapp.ui.theme.Pink80
import com.example.noteapp.ui.theme.Purple40
import com.example.noteapp.ui.theme.Purple80
import com.example.noteapp.ui.theme.PurpleGrey40
import com.example.noteapp.ui.theme.PurpleGrey80
import com.example.noteapp.ui.theme.Redd
import com.example.noteapp.ui.theme.Whiet
import com.example.noteapp.ui.theme.Yellow


@Entity
data class Note(
   @PrimaryKey(autoGenerate = true) val id :Int = 0,
    val title:String,
    val description:String,
    val isFavorite :Boolean,
    val color :Int

)

{
    companion object{
        val noteColors= listOf(Whiet, BabyBlue, BabyBlue1, Purple80, PurpleGrey80, Pink80, Purple40,
            PurpleGrey40, Yellow, Blue, Green, Orang, Redd)
    }
}
