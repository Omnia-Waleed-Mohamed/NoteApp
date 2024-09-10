package com.example.noteapp.di

import android.content.Context
import androidx.room.Room
import com.example.noteapp.data.dp.NoteDao
import com.example.noteapp.data.dp.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context):NoteDatabase{
        return Room.databaseBuilder(
            context.applicationContext,
            NoteDatabase::class.java,
            "user_database"
        ).build()
    }

@Provides
@Singleton
fun provideNoteDao(noteDatabase:NoteDatabase) : NoteDao{
    return noteDatabase.noteDao()
}}