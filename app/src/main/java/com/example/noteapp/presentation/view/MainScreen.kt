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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.noteapp.R
import com.example.noteapp.data.dp.Note
import com.example.noteapp.presentation.viewModel.NoteViewModel

class MainScreen(val noteViewModel: NoteViewModel):Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

            val selectedIndex= remember {
                mutableStateOf(0)
            }
            val  showDialog= remember {
                mutableStateOf(false)
            }


        val navigator = LocalNavigator.currentOrThrow
           val screen = listOf(
               HomeScreenContent(noteViewModel = noteViewModel),
                FavoriteScreen(noteViewModel = noteViewModel)
           )
            Scaffold(
                topBar = {
                    TopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White, titleContentColor = Color(0xFFf09322)),
                        title = {
                            val appTitle = stringResource(id = R.string.app_title)
                            Text(appTitle,  fontSize = 30.sp) },
                        actions = {
                            IconButton(onClick = {  }) {
                                Icon(imageVector = Icons.Filled.Menu, contentDescription = "More options",tint = Color(0xFFf09322), modifier = Modifier.size(30.dp))
                            }}
                    )
                },
                floatingActionButton = {
                    FloatingActionButton(containerColor = Color(0xFFf09322) ,
                        onClick = {showDialog.value=true
                                  }

                        ) {
                        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add", tint = Color.White)
                    }
                }
                ,
                bottomBar = { BottomAppBar(containerColor = Color.White) {
                    IconButton(modifier = Modifier.weight(1f),

                        onClick = { selectedIndex.value=0 }) {
                        Icon(modifier = Modifier.size(50.dp), imageVector = if(selectedIndex.value==0) Icons.Filled.Home else Icons.Outlined.Home,contentDescription = null, tint = Color(0xFFf09322))

                    }
                    IconButton(  modifier = Modifier.weight(1f),
                        onClick = { selectedIndex.value=1 }) {
                        Icon(modifier = Modifier.size(50.dp),imageVector = if(selectedIndex.value==1) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,contentDescription = null, tint = Color(0xFFf09322))

                    }
                }
                },
                modifier = Modifier.fillMaxSize()
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                       if(showDialog.value)
                    {
                        showAddDialog(onSaveClick = {title,description,selectColor->
                            noteViewModel.insertNote(Note(title=title,description=description,isFavorite=false,color=selectColor))
                            showDialog.value=false

                        }
                            , onDismissRequest = {
                                showDialog.value=false
                            })
                    }




                    if (selectedIndex.value==0)
                    {
                        HomeScreenContent(noteViewModel = noteViewModel)
                    }else
                    {
                        FavoriteScreen(noteViewModel = noteViewModel)
                    }
                    //HomeScreenContent(noteViewModel = noteViewModel)
                }



            }

    }}

@Composable
fun showAddDialog(onSaveClick:(String,String,Int)->Unit,onDismissRequest:()->Unit) {
    val title = remember {
        mutableStateOf("")
    }
    val description = remember {
        mutableStateOf("")
    }
    val selectedColor = remember { mutableStateOf(Color.White) }
     val context = LocalContext.current

    AlertDialog(onDismissRequest = { onDismissRequest() },
        icon = {
            Column(modifier = Modifier.clickable { onDismissRequest() }) {
                Row {
                    Text(text = "Add Note", color = Color(0xFFf09322), fontSize = 30.sp)

                }
            }
        },
        confirmButton = {
            Button(

                onClick = { onSaveClick(title.value, description.value,selectedColor.value.toArgb())
                    Toast.makeText(context, "Save Note", Toast.LENGTH_SHORT).show()},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFf09322)

                )

            ) {
                Text(text = "Save")
            }

        },
//title = { Text(text = "Add Note", color = Color(0xFF8A2BE2))},
        
        text = {
            Column(modifier = Modifier) {
                OutlinedTextField(
                    value = title.value,
                    onValueChange = { newvalue ->
                        title.value = newvalue
                    },
                    placeholder = { Text(text = "Title", color = Color.Gray) },
                    modifier = Modifier.border(
                        width = 2.dp,
                        color = Color(0xFFf09322),
                        shape = RoundedCornerShape(16.dp)
                    ).clip(RoundedCornerShape(16.dp))
                        .fillMaxWidth()

                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = description.value,
                    onValueChange = { newvalue ->
                        description.value = newvalue
                    },
                    placeholder = { Text(text = "Description", color = Color.Gray) },
                    modifier = Modifier.height(150.dp)
                        .border(
                            width = 2.dp,
                            color = Color(0xFFf09322),
                            shape = RoundedCornerShape(16.dp)
                        ).clip(RoundedCornerShape(16.dp))
                        .fillMaxWidth()

                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Select Color:",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFf09322)
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                ) {
                    Note.noteColors.forEach { color ->
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(color)
                                .border(
                                    width = 2.dp,
                                    color = if (selectedColor.value == color) Color.Black else Color.Transparent,
                                    shape = CircleShape
                                )
                                .clickable { selectedColor.value = color }
                        )


                    }

                }

            }
        }
    )
}


