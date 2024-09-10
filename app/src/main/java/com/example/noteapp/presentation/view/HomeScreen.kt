package com.example.noteapp.presentation.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.noteapp.R
import com.example.noteapp.utils.SwipeToDeleteContainer
import com.example.noteapp.presentation.viewModel.NoteViewModel

@Composable
fun HomeScreenContent(noteViewModel: NoteViewModel){

    val allNote = noteViewModel.allNotes.collectAsState()
    val navigator = LocalNavigator.currentOrThrow

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(allNote.value ?: emptyList(), key = { note -> note.id }) { note ->
            val cardColor = Color(note.color)

            SwipeToDeleteContainer(
                item = note,
                onDelete = { deletedNote ->
                    noteViewModel.deleteNote(deletedNote)
                },
                content = { currentNote ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { navigator.push(NoteDetailsScreen(currentNote, noteViewModel)) }
                            .padding(10.dp)
                            .clip(RoundedCornerShape(20.dp)),
                        colors = CardDefaults.cardColors(containerColor = cardColor),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                        shape = RoundedCornerShape(0.dp)
                    ) {Row() {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(10.dp)
                            ) {
                                Text(text = currentNote.title, fontSize = 20.sp)
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(text = currentNote.description, fontSize = 15.sp)
                            }
                            IconButton(onClick = { noteViewModel.updateNote(currentNote.copy(isFavorite = !currentNote.isFavorite)) }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_favorite_24),
                                    contentDescription = null,
                                    tint = if (currentNote.isFavorite) Color(0xFFf09322) else Color.White
                                )
                            }
                        }
                    }
                }
            )
        }
    }

}


