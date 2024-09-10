package com.example.noteapp.presentation.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.noteapp.data.dp.Note
import com.example.noteapp.presentation.viewModel.NoteViewModel

class  NoteDetailsScreen(val note: Note,val noteViewModel: NoteViewModel) : Screen{
    @Composable
    override fun Content() {
        val title = remember {
            mutableStateOf(note.title)
        }
        val description = remember {
            mutableStateOf(note.description)
        }
        val context = LocalContext.current
        val selectedColor = remember { mutableStateOf(note.color) }
        val navigator = LocalNavigator.currentOrThrow
        Column {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                Row() {
                    Text(text = "Edit Note", fontSize = 30.sp, color = Color(0xFFf09322))
                    Spacer(modifier = Modifier.width(185.dp))
                    IconButton(onClick = {
                        noteViewModel.updateNote(
                            note.copy(
                                title = title.value,
                                description = description.value,
                                color = selectedColor.value
                            )
                        )
                        navigator.pop()
                        Toast.makeText(context, "Edit Note", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(
                            Icons.Default.Done,
                            contentDescription = null,
                            tint = Color(0xFFf09322),
                            modifier = Modifier.size(40.dp)
                        )

                    }
                }
                OutlinedTextField(
                    modifier = Modifier
                        .border(
                            width = 2.dp,
                            color = Color(0xFFf09322),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clip(RoundedCornerShape(16.dp))
                        .fillMaxWidth(), value = title.value, onValueChange = { newvalue ->
                        title.value = newvalue
                    }, placeholder = { Text(text = "Title") })
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(modifier = Modifier
                    .height(400.dp)
                    .border(
                        width = 2.dp,
                        color = Color(0xFFf09322),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clip(RoundedCornerShape(16.dp))
                    .fillMaxWidth(), value = description.value, onValueChange = { newvalue ->
                    description.value = newvalue
                }, placeholder = { Text(text = "Description") })

                Spacer(modifier = Modifier.height(10.dp))
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                ) {
                    Note.noteColors.forEach { color ->
                        Box(
                            modifier = Modifier
                                .size(70.dp)
                                .clip(CircleShape)
                                .background(color)
                                .border(
                                    width = 2.dp,
                                    color = if (selectedColor.value == color.toArgb()) Color.Black else Color.Transparent,
                                    shape = CircleShape
                                )
                                .clickable { selectedColor.value = color.toArgb() }
                        )
                    }

                }


            }
        }
    }}

