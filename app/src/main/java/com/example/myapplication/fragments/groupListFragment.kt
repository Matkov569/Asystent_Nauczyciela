package com.example.myapplication.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MainViewModel
import com.example.myapplication.R
import com.example.myapplication.adapters.groupAdapter
import com.example.myapplication.adapters.lectureAdapter
import com.example.myapplication.androidVM.groupStudVM
import com.example.myapplication.androidVM.groupVM
import com.example.myapplication.androidVM.lectureVM
import com.example.myapplication.androidVM.todoVM
import com.example.myapplication.data.group
import com.example.myapplication.data.toDo

class groupListFragment : Fragment() {

    lateinit var GroupVM: groupVM;
    lateinit var GroupStudVM: groupStudVM;
    lateinit var LectureVM:lectureVM;
    lateinit var toDoVM: todoVM;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_grouplist,container,false);

        val viewModel by activityViewModels<MainViewModel>()

        val adapter = groupAdapter(viewModel)
        val recyclerView = view.findViewById<RecyclerView>(R.id.groupListRecycler);
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        toDoVM = ViewModelProvider(this).get(todoVM::class.java)
        LectureVM = ViewModelProvider(this).get(lectureVM::class.java)
        GroupStudVM = ViewModelProvider(this).get(groupStudVM::class.java)
        GroupVM = ViewModelProvider(this).get(groupVM::class.java)

        var counts = mutableListOf<Int>();

        GroupVM.getListForLect(LectureVM.getList()[viewModel.currentLecture].id).forEach {
            counts.add(GroupStudVM.getForGroup(it.id).size)
        }

        GroupVM.getAllForLect(LectureVM.getList()[viewModel.currentLecture].id).observe(viewLifecycleOwner,
            Observer { group ->
            adapter.setData(group, counts as List<Int>)
        })


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel by activityViewModels<MainViewModel>()

        var actLect = LectureVM.getList()[viewModel.currentLecture];
        (view.findViewById<TextView>(R.id.toolbarShortNameText)).setText(actLect?.Skrot);
        (view.findViewById<TextView>(R.id.toolbarFullNameText)).setText(actLect?.Nazwa);

        (view.findViewById<ImageButton>(R.id.return_btn)).setOnClickListener {
            it.findNavController().navigate(R.id.action_groupListFragment_to_welcomeFragment2)
        }

        (view.findViewById<Button>(R.id.addGroupBtn)).setOnClickListener {
            it.findNavController().navigate(R.id.action_groupListFragment_to_groupAdd)
        }

        (view.findViewById<Button>(R.id.editLectureBtn)).setOnClickListener {
            viewModel.lectureEdit=true;
            it.findNavController().navigate(R.id.action_groupListFragment_to_lectureAddFragment)
        }

        (view.findViewById<Button>(R.id.deleteLectureBtn)).setOnClickListener {
            var builder = AlertDialog.Builder(context);
            builder.setTitle("Usuwanie przedmiotu");
            builder.setMessage("Czy na pewno chcesz usunąć przedmiot?");

            builder.setPositiveButton("Tak") { dialog, which ->
                toDoVM.newToDo(toDo("Usunięto przedmiot",actLect.Nazwa))
                LectureVM.deleteLecture(actLect)
                it.findNavController().navigate(R.id.action_groupListFragment_to_welcomeFragment2)
                Toast.makeText(context,"Usunięto przedmiot",Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("Nie") { dialog, which ->

            }
            builder.show()
        }
    }
}