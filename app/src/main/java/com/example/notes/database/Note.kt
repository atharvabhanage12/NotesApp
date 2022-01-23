package com.example.notes.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
class Note(

    @ColumnInfo(name= "note_title",defaultValue = "deFault")
    val title:String,

    @ColumnInfo(name = "note_text")
    val text:String ,

    @ColumnInfo(name = "latest_change")
    val datetime : String



){
    @PrimaryKey(autoGenerate = true)
    var id=0

}