package com.example.notes.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notes.NoteViewModel
import com.example.notes.R
import com.example.notes.databinding.FragmentDetailsBinding
import com.example.notes.databinding.FragmentEditDetailsBinding
import kotlinx.coroutines.flow.combine
import java.text.SimpleDateFormat
import java.util.*


@SuppressLint("SimpleDateFormat")
private val sdf= SimpleDateFormat("yyyy/MM/dd HH:mm:ss")

class EditDetailsFragment : Fragment() {

    private lateinit var viewmodel:NoteViewModel
//    private var binding: FragmentEditDetailsBinding?=null

    val flag: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

//    private lateinit var passEdittedText :String
    private val args : EditDetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            setHasOptionsMenu(true)
            flag.value=1

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.save_menu,menu)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        return when (item.itemId){
            R.id.save_bar->{

//                passTextFun()
                flag.value=0
//                val textToSave:String= "binding?.editText.toString()"
//                viewmodel.updateNode(args.editid,textToSave)
//
//                val action= EditDetailsFragmentDirections.actionEditDetailsFragmentToDetailsFragment(args.editid)
//                view?.findNavController()?.navigate(action)
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

        val binding : FragmentEditDetailsBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_edit_details,container,false)



        binding.editText.setText(args.editid.toString())

        viewmodel= ViewModelProvider(this).get(NoteViewModel::class.java)


        textdisplay(args.editid,binding,viewmodel)

        flag.observe(viewLifecycleOwner, {
//            flag.value=1
            if(it==0){
                val textToSave:String= binding.editText.text.toString()
                val titleToSave:String =binding.editdetailstitle.text.toString()
                val now = sdf.format(Date())
                viewmodel.updateNode(args.editid,titleToSave,textToSave,now)



                val action= EditDetailsFragmentDirections.actionEditDetailsFragmentToDetailsFragment(args.editid)
                view?.findNavController()?.navigate(action)

            }



        })
//        =binding.editText.toString()

    //        viewmodel.updateNode(args.editid,)   //here we need to input the text that is in editText

        return binding.root
    }

    private fun textdisplay(query: Int?,binding : FragmentEditDetailsBinding,viewmodel: NoteViewModel){
        if (query != null) {
            val answer=viewmodel.searchtext(query)
            answer.observe(viewLifecycleOwner,{list->
                binding.editText.setText(list[0].text)
                binding.editdetailstitle.setText(list[0].title)

            })

        }

    }






}