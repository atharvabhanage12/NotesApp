package com.example.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.database.Note
import com.example.notes.databinding.ActivityMainBinding
import com.example.notes.recyclerview.INotesRVAdapter
import com.example.notes.recyclerview.NotesRVAdapter


//private lateinit var binding: ActivityMainBinding
class MainActivity : AppCompatActivity(), INotesRVAdapter {



    lateinit var viewModel: NoteViewModel
    private var layoutManager: RecyclerView.LayoutManager?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding=ActivityMainBinding.inflate(layoutInflater)
//        setContentView(R.layout.activity_main)



        layoutManager= LinearLayoutManager(this)
        binding.recyclerView.layoutManager=layoutManager
        val adapter= NotesRVAdapter(this,this)


        binding.recyclerView.adapter=adapter


        viewModel= ViewModelProvider(this).get(NoteViewModel::class.java)

        viewModel.allNotes.observe(this, Observer {list->
            list?.let {
                adapter.updateList(it)
            }

        })

        setContentView(binding.root)


    }

    override fun onItemClicked(note: Note) {
        viewModel.deleteNode(note)
        Toast.makeText(this,"${note.text} Deleted",Toast.LENGTH_LONG).show()
    }


    fun submitData(view: View) {
//       var binding: ActivityMainBinding
//        val binding=ActivityMainBinding.inflate(layoutInflater)
        val noteText:EditText =  findViewById(R.id.input)
//        val nnn="ksks"

//        Toast.makeText(this,"clicked ${noteText.text}",Toast.LENGTH_LONG).show()

        val noteTexts=  noteText.text.toString()
        
        if(noteTexts.isNotEmpty()){
            viewModel.insertNode(Note(noteTexts))
            Toast.makeText(this,"${noteTexts} Inserted",Toast.LENGTH_LONG).show()
        }
    }
}