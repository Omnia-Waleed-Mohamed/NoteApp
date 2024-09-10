package com.example.noteapp.presentation.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.noteapp.presentation.viewModel.NoteViewModel

@Composable
fun FavoriteScreen (noteViewModel: NoteViewModel){
    val allNotes = noteViewModel.allNotes.collectAsState()
    val navigator = LocalNavigator.currentOrThrow
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(allNotes.value?.filter { it.isFavorite }?: emptyList() ){ note->
            val cardColor = Color(note.color)
            Card(colors= CardDefaults.cardColors(containerColor = cardColor),elevation = CardDefaults.cardElevation(10.dp),modifier = Modifier
                .fillMaxWidth()
                .clickable { navigator.push(NoteDetailsScreen(note, noteViewModel)) }
                .padding(13.dp)
                ) {


                Column(modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()) {
                    Row (verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()){


                        Text(text = note.title, fontSize = 20.sp,
                            modifier = Modifier.weight(1f))

                        Icon(
                            modifier = Modifier
                                .clickable {
                                    noteViewModel.updateNote(note.copy(isFavorite = !note.isFavorite))
                                }
                                .size(30.dp),
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = null,
                            tint = if (note.isFavorite) Color(0xFFf09322) else Color.White)

                    }
                        Spacer(modifier = Modifier.height(10.dp))

                        Text(text = note.description, fontSize = 15.sp)

                }

            }
        }
    }
}