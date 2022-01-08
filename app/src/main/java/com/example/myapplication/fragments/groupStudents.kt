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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MainViewModel
import com.example.myapplication.R
import com.example.myapplication.adapters.groupAdapter
import com.example.myapplication.adapters.groupStudAdapter
import com.example.myapplication.androidVM.*
import com.example.myapplication.data.groupStudent
import com.example.myapplication.data.toDo
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class groupStudents : Fragment() {

    lateinit var GroupVM: groupVM;
    lateinit var GroupStudVM: groupStudVM;
    lateinit var LectureVM: lectureVM;
    lateinit var StudentVM: studentVM;
    lateinit var StudLectVM: studlectVM;
    lateinit var toDoVM: todoVM;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_group_students, container, false)

        toDoVM = ViewModelProvider(this).get(todoVM::class.java)
        LectureVM = ViewModelProvider(this).get(lectureVM::class.java)
        StudentVM = ViewModelProvider(this).get(studentVM::class.java)
        StudLectVM = ViewModelProvider(this).get(studlectVM::class.java)
        GroupVM = ViewModelProvider(this).get(groupVM::class.java)
        GroupStudVM = ViewModelProvider(this).get(groupStudVM::class.java)

        val viewModel by activityViewModels<MainViewModel>()

        viewModel.selectedStudents.clear()
        viewModel.bannedStudents.clear()

        println(GroupStudVM.getAll().toString())

        println(GroupVM.getListForLect(LectureVM.getList()[viewModel.currentLecture].id)[viewModel.currentGroup].id)

        viewModel.selectedStudents = GroupStudVM.getForGroup(GroupVM.getListForLect(LectureVM.getList()[viewModel.currentLecture].id)[viewModel.currentGroup].id);
        println(viewModel.selectedStudents)

        var unionedList = mutableListOf<Long>();

        var groups = GroupVM.getListForLect(LectureVM.getList()[viewModel.currentLecture].id)
        groups.forEach {
            if(it.id != GroupVM.getListForLect(LectureVM.getList()[viewModel.currentLecture].id)[viewModel.currentGroup].id)
                unionedList = unionedList.union(GroupStudVM.getForGroup(it.id)).toMutableList()
        }

        println(unionedList.toString())

        viewModel.bannedStudents = unionedList;

        val adapter = groupStudAdapter(viewModel)
        val recyclerView = view.findViewById<RecyclerView>(R.id.groupStudentsRV);
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        StudLectVM.getStudentsFromLect(LectureVM.getList()[viewModel.currentLecture].id).observe(viewLifecycleOwner, Observer { group ->
            adapter.setData(group)
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel by activityViewModels<MainViewModel>()

        var lecture = LectureVM.getList()[viewModel.currentLecture]
        var group = GroupVM.getListForLect(lecture.id)[viewModel.currentGroup];

        (view.findViewById<TextView>(R.id.toolbarText)).setText(lecture.Skrot+" - "+group.Nazwa);

        (view.findViewById<ImageButton>(R.id.return_btn)).setOnClickListener {
            it.findNavController().navigate(R.id.action_groupStudents_to_groupDetails)
        }
        (view.findViewById<Button>(R.id.grStSaveBtn)).setOnClickListener {

            runBlocking {
                launch {
                    delay(50)
                    viewModel.selectedStudents.forEach {
                        GroupStudVM.insert(groupStudent(group.id,it))
                    }
                }
                launch {
                    delay(100)
                    it.findNavController().navigate(R.id.action_groupStudents_to_groupDetails)
                }
                GroupStudVM.deleteGroup(group.id)
            }


        }
    }
}