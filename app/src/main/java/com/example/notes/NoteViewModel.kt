package com.example.notes

import android.app.Application
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.*
import com.example.notes.database.Note
import com.example.notes.database.NoteDao
import com.example.notes.database.NoteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    val allNotes: LiveData<List<Note>>
    private val repository: NoteRepository

    init {
        val dao= NoteDatabase.getDatabase(application).getNoteDao()
        repository=NoteRepository(dao)
        allNotes= repository.allNotes
    }
///  can give an error
    fun deleteNode(note : Note) = viewModelScope.launch(Dispatchers.IO){
        repository.delete(note)
    }
    fun insertNode(note : Note) = viewModelScope.launch(Dispatchers.IO){
        repository.insert(note)
    }
    fun updateNode(givenid:Int,query_text:String) =viewModelScope.launch(Dispatchers.Default) {
        repository.update(givenid,query_text)
    }

    fun searchDatabase(searchQuery: String) : LiveData<List<Note>>{
        return repository.searchNotes(searchQuery)
    }

    fun searchtext(searchid:Int) : LiveData<List<Note>>{
        return repository.searchnote(searchid)
    }


}