package com.example.notes

import androidx.lifecycle.LiveData
import com.example.notes.database.Note

import com.example.notes.database.NoteDao

class NoteRepository(private val noteDoa:NoteDao) {

    val allNotes: LiveData<List<Note>> =noteDoa.getAllNotes()

    fun searchNotes(searchQuery : String) : LiveData<List<Note>> {
        return noteDoa.searchDatabase(searchQuery)
    }

    fun searchnote(searchQuery: Int) : LiveData<List<Note>>{
        return  noteDoa.searchtext(searchQuery)
    }

    suspend fun insert(note:Note){
        noteDoa.insert(note)
    }
    suspend fun delete(note:Note){
        noteDoa.delete(note)
    }
    suspend fun update(givenid:Int,query_title:String,query_text:String,latest_time:String){
        noteDoa.update(givenid,query_title,query_text,latest_time)
    }

}