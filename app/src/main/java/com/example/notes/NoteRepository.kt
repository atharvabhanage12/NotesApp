package com.example.notes

import androidx.lifecycle.LiveData
import com.example.notes.database.Note

import com.example.notes.database.NoteDao

class NoteRepository(private val noteDoa:NoteDao) {

    val allNotes: LiveData<List<Note>> =noteDoa.getAllNotes()

    suspend fun insert(note:Note){
        noteDoa.insert(note)
    }
    suspend fun delete(note:Note){
        noteDoa.delete(note)
    }
}