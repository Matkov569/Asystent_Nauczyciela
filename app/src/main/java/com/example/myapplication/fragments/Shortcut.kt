package com.example.myapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MainViewModel
import com.example.myapplication.R
import com.example.myapplication.adapters.lectureAdapter
import com.example.myapplication.data.toDo
import com.example.myapplication.adapters.toDoAdapter
import com.example.myapplication.androidVM.lectureVM
import com.example.myapplication.androidVM.todoVM

class Shortcut : Fragment() {

    private lateinit var toDoVM:todoVM;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_shortcut,container,false);
        // = ViewModelProvider(requireActivity()).get(lectureVM::class.java);
        val viewModel by activityViewModels<MainViewModel>()

        toDoVM = ViewModelProvider(requireActivity()).get(todoVM::class.java)

        var adapter = toDoAdapter(viewModel,toDoVM)
        val recyclerView = view.findViewById<RecyclerView>(R.id.toDoRV);
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        toDoVM.toDos.observe(viewLifecycleOwner, Observer { lecture ->
            adapter.setData(lecture)
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (view.findViewById<TextView>(R.id.toolbarText)).setText("Wprowadzone zmiany");

        (view.findViewById<ImageButton>(R.id.return_btn)).setOnClickListener {
            it.findNavController().navigate(R.id.action_shortcut2_to_welcomeFragment2)
        }
    }



}