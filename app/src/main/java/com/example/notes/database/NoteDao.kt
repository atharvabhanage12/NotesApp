package com.example.notes.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note:Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("UPDATE notes_table SET note_text = :query_text WHERE id =:givenid")
    suspend fun update(givenid:Int,query_text:String)

    @Query("SELECT * FROM notes_table order by id ASC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM notes_table WHERE note_text LIKE :searchQuery")
    fun searchDatabase(searchQuery:String) : LiveData<List<Note>>

    @Query("SELECT * FROM notes_table WHERE id = :searchid")
    fun searchtext(searchid:Int) : LiveData<List<Note>>

}