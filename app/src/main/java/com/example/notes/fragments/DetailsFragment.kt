package com.example.notes.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.example.notes.NoteViewModel
import com.example.notes.R
import com.example.notes.database.Note
import com.example.notes.databinding.FragmentDetailsBinding
import kotlin.properties.Delegates


class DetailsFragment : Fragment() {

    private var idtemp by Delegates.notNull<Int>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            setHasOptionsMenu(true)

        }
    }
//
//
//
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.edit_menu,menu)
//    val edit = menu.findItem(R.id.edit_bar)
//    Log.i("menu inflate","inflated EDit working")
//
//    val action= DetailsFragmentDirections.actionDetailsFragmentToEditDetailsFragment()
//    view?.findNavController()?.navigate(action)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        return when (item.itemId){
            R.id.edit_bar->{

                val action= DetailsFragmentDirections.actionDetailsFragmentToEditDetailsFragment(idtemp)
                view?.findNavController()?.navigate(action)
                true
            }
            else->{
                false
            }
        }
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         val binding : FragmentDetailsBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_details,container,false)

        val args : DetailsFragmentArgs by navArgs()


        val viewmodel= ViewModelProvider(this).get(NoteViewModel::class.java)

        idtemp=args.noteid

        textdisplay(args.noteid,binding,viewmodel)







        return binding.root
    }



    private fun textdisplay(query: Int?,binding : FragmentDetailsBinding,viewmodel: NoteViewModel){
        if (query != null) {
            val answer=viewmodel.searchtext(query)
            answer.observe(this,{list->
                binding.ouputText.text= list[0].text

            })

        }

    }



}
