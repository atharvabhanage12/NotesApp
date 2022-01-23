package com.example.notes.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note:Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("UPDATE notes_table SET note_text = :query_text , note_title= :query_title , latest_change= :latest_time  WHERE id =:givenid")
    suspend fun update(givenid:Int,query_title:String,query_text:String,latest_time:String)

    @Query("SELECT * FROM notes_table order by latest_change DESC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM notes_table WHERE note_title LIKE :searchQuery or note_text LIKE :searchQuery")
    fun searchDatabase(searchQuery:String) : LiveData<List<Note>>

    @Query("SELECT * FROM notes_table WHERE id = :searchid")
    fun searchtext(searchid:Int) : LiveData<List<Note>>

}