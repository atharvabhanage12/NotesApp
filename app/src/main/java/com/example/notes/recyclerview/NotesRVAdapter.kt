package com.example.notes.recyclerview

import android.content.Context
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.database.Note
import org.w3c.dom.Text
import kotlin.math.log


class NotesRVAdapter(private val context : Context?, private val listner:INotesRVAdapter,private val listner_1:INotesRVAdapter_1) :RecyclerView.Adapter<NotesRVAdapter.NoteViewHolder>() {


    val allNotes = ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {


        val viewHolder= NoteViewHolder(LayoutInflater.from(context).inflate(R.layout.item_note,parent,false))

        viewHolder.deleteButton.setOnClickListener {
            listner.onItemClicked(allNotes[viewHolder.adapterPosition])
        }
        viewHolder.textView.setOnClickListener {
            listner_1.onItemClicked_1(allNotes[viewHolder.adapterPosition])
        }


        return viewHolder
    }
    override fun getItemCount(): Int {
        Log.i("RV",allNotes.size.toString())
        return allNotes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        Log.i("RV postion",position.toString())
        val currenNote= allNotes[position]
        holder.textView.text= currenNote.text
    }



    fun updateList(newList: List<Note>){
        allNotes.clear()
        allNotes.addAll(newList)

        notifyDataSetChanged()
    }


    inner class NoteViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        var textView:TextView=itemView.findViewById(R.id.text)
        val deleteButton:ImageView =itemView.findViewById(R.id.deleteButton)

    }


}

interface INotesRVAdapter{
    fun onItemClicked(note: Note)
}
interface INotesRVAdapter_1{
    fun onItemClicked_1(note: Note)
}