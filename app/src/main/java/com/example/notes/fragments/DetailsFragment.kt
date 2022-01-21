package com.example.notes.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.notes.NoteViewModel
import com.example.notes.R
import com.example.notes.database.Note
import com.example.notes.databinding.FragmentContentBinding
import com.example.notes.databinding.FragmentDetailsBinding


class DetailsFragment : Fragment() {

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//
//        }
//    }
//    private lateinit var binding:FragmentDetailsBinding
//    lateinit var viewmodel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         val binding : FragmentDetailsBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_details,container,false)

        val args : DetailsFragmentArgs by navArgs()

//        textdisplay()
        val viewmodel= ViewModelProvider(this).get(NoteViewModel::class.java)
        textdisplay(args.noteid,binding,viewmodel)


        return binding.root
    }

//    fun textdisplay(note: Note) {
////        viewModel.deleteNode(note)
//        Toast.makeText(context,"${note.text} Deleted", Toast.LENGTH_SHORT).show() //
//    }



    private fun textdisplay(query: Int?,binding : FragmentDetailsBinding,viewmodel: NoteViewModel){
        if (query != null) {
            val answer=viewmodel.searchtext(query)
            answer.observe(this,{list->
                binding.ouputText.text= list[0].text

            })

        }
//        val searchQuery= "%$query%"
//
//        viewModel.searchDatabase(searchQuery).observe(this,{list->
//            list.let{adapter.updateList(it) }
//        })
    }



}
