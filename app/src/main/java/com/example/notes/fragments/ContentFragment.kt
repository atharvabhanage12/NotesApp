package com.example.notes.fragments

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.ActionBarContextView
import androidx.appcompat.widget.SearchView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.MainActivity
import com.example.notes.NoteViewModel
import com.example.notes.R
import com.example.notes.database.Note
import com.example.notes.databinding.ActivityMainBinding
//import com.example.notes.databinding.ActivityMainBinding.inflate
import com.example.notes.databinding.FragmentContentBinding
import com.example.notes.recyclerview.INotesRVAdapter
import com.example.notes.recyclerview.INotesRVAdapter_1
import com.example.notes.recyclerview.NotesRVAdapter


class ContentFragment :  Fragment(), INotesRVAdapter, INotesRVAdapter_1,SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentContentBinding

    lateinit var viewModel: NoteViewModel
    private var layoutManager: RecyclerView.LayoutManager?=null
    private lateinit var adapter : NotesRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu,menu)
        val search = menu.findItem(R.id.search_bar)
        Log.i("menu inflate","inflated working")
        val searchView =search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled =true
        searchView?.setOnQueryTextListener(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding : FragmentContentBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_content,container,false)


        layoutManager= LinearLayoutManager(context)  //
        binding.recyclerView.layoutManager=layoutManager




        binding.button.setOnClickListener {
            submitData(binding)
        }

        adapter= NotesRVAdapter( this.context ,this,this) //


        binding.recyclerView.adapter=adapter


        viewModel= ViewModelProvider(this).get(NoteViewModel::class.java)

        viewModel.allNotes.observe(viewLifecycleOwner, Observer {list->   //
            list?.let {
                adapter.updateList(it)
            }

        })





        return binding.root
    }

    override fun onItemClicked(note: Note) {
        viewModel.deleteNode(note)
        Toast.makeText(context,"Note Deleted", Toast.LENGTH_SHORT).show() //
    }


    fun submitData(binding: FragmentContentBinding) {
//       var binding: FragmentContentBinding
//        val binding=ActivityMainBinding.inflate(layoutInflater)
        val noteText: EditText = binding.input // findViewById(R.id.input)
//        val nnn="ksks"

//        Toast.makeText(this,"clicked ${noteText.text}",Toast.LENGTH_LONG).show()

        val noteTexts=  noteText.text.toString()

        if(noteTexts.isNotEmpty()){
            viewModel.insertNode(Note(noteTexts))
            Toast.makeText(context,"${noteTexts} Inserted", Toast.LENGTH_SHORT).show()
        }
    }

    //////////////////////////////////////////////////////

    override fun onItemClicked_1(note: Note) {
        Log.v("nav","navigaton")
        val action= ContentFragmentDirections.actionContentFragmentToDetailsFragment(note.text,note.id)
        view?.findNavController()?.navigate(action)
//        Toast.makeText(context,"${note.text} selected id ${note.id}", Toast.LENGTH_SHORT).show()


    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query!=null){
            searchDatabase(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        if(query!=null){
            searchDatabase(query)
        }
        return true
    }

    private fun searchDatabase(query: String?){
        val searchQuery= "%$query%"

        viewModel.searchDatabase(searchQuery).observe(this,{list->
            list.let{adapter.updateList(it) }
        })
    }
}