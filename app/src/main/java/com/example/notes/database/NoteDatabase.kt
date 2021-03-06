package com.example.notes.database


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Note::class), version = 3, exportSchema = false)
abstract class NoteDatabase:RoomDatabase()  {


    abstract fun getNoteDao() : NoteDao


    companion object {


        @Volatile
        private var INSTANCE: NoteDatabase? = null

        fun getDatabase(context: Context): NoteDatabase {
            return INSTANCE?:synchronized(this) {
                val instance =   Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "note_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }

            }
        }
    }
