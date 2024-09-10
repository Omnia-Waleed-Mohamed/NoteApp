package com.example.noteapp.presentation.viewModel

import android.app.Application
import androidx.compose.ui.Modifier
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.data.dp.Note
import com.example.noteapp.data.dp.NoteDao
import com.example.noteapp.data.dp.NoteDatabase
import com.example.noteapp.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.w3c.dom.Node
import javax.inject.Inject

//val noteRepository:NoteRepository
@HiltViewModel
class NoteViewModel @Inject constructor(val noteRepository:NoteRepository):ViewModel()
    //AndroidViewModel(application)
{
    //val noteDao =NoteDatabase.getDatabase(application).noteDao()

    private val _allNotes = MutableStateFlow<List<Note>?>(null)
    val allNotes : StateFlow<List<Note>?> get() = _allNotes

    init{
        getAllNotes()
    }

    fun getAllNotes(){
        viewModelScope.launch {
            _allNotes.value = noteRepository.getAll()
            println("All notes: ${_allNotes.value}")
        }
    }

    fun insertNote(note:Note){
        viewModelScope.launch {
            noteRepository.insert(note)
            getAllNotes()
        }
    }
    fun updateNote(note:Note){
        viewModelScope.launch {
            noteRepository.update(note)
            getAllNotes()

        }
    }

    fun getNoteById(id:Int){
        viewModelScope.launch {
            noteRepository.getNoteById(id)
            getAllNotes()
        }
    }

    fun deleteNote(note:Note){
        viewModelScope.launch {
            noteRepository.deleteNote(note)
            getAllNotes()
        }
    }




}