package com.example.noteapp.data.repository

import com.example.noteapp.data.dp.Note
import com.example.noteapp.data.dp.NoteDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepository @Inject constructor (val noteDao : NoteDao) {

    suspend fun getAll():List<Note>{
        return noteDao.getAll()
    }

    suspend fun getFavorites():List<Note>{
        return noteDao.getFavorites()
    }
    suspend fun insert(note:Note){
        noteDao.insert(note)
    }

    suspend fun update(note:Note) = noteDao.update(note)

    suspend fun deleteNote(note: Note){
        noteDao.deleteNote(note)
    }

    suspend fun getNoteById(id :Int)=noteDao.getById(id)

}