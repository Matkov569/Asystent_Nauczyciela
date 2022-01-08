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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MainViewModel
import com.example.myapplication.R
import com.example.myapplication.adapters.studentAdapter
import com.example.myapplication.adapters.studentsGradesAdapter
import com.example.myapplication.androidVM.*
import com.example.myapplication.data.student

class GroupGrades : Fragment() {

    lateinit var StudLectVM: studlectVM;
    lateinit var GroupStudVM: groupStudVM;
    lateinit var GradeStudVM: gradeStudVM;
    lateinit var LectureVM: lectureVM;
    lateinit var GradeVM: gradeVM;
    lateinit var GroupVM: groupVM;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_group_grades, container, false)

        val viewModel by activityViewModels<MainViewModel>()
        LectureVM = ViewModelProvider(this).get(lectureVM::class.java)
        GradeVM = ViewModelProvider(this).get(gradeVM::class.java)
        GroupVM = ViewModelProvider(this).get(groupVM::class.java)
        StudLectVM = ViewModelProvider(this).get(studlectVM::class.java)
        GroupStudVM = ViewModelProvider(this).get(groupStudVM::class.java)
        GradeStudVM = ViewModelProvider(this).get(gradeStudVM::class.java)

        var lecture = LectureVM.getList()[viewModel.currentLecture]
        var group = GroupVM.getListForLect(lecture.id)[viewModel.currentGroup]
        var students = GroupStudVM.getForGroup(group.id)
        var required = GradeVM.getRequired(lecture.id).size

        var grades = mutableListOf<String>()

        students.forEach {
            grades.add(GradeStudVM.getRequiredForStud(it,lecture.id).size.toString()+"/"+required)
        }
        if(students.size>0) {
            val adapter = studentsGradesAdapter(viewModel)
            val recyclerView = view.findViewById<RecyclerView>(R.id.studentsGradesRV);
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())


            GroupStudVM.getGroup(group.id)
                .observe(viewLifecycleOwner, Observer { student ->
                    adapter.setData(student, grades as List<String>)
                })
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel by activityViewModels<MainViewModel>()

        var lecture = LectureVM.getList()[viewModel.currentLecture]
        var group = GroupVM.getListForLect(lecture.id)[viewModel.currentGroup]

        (view.findViewById<TextView>(R.id.toolbarText)).setText(lecture.Skrot+" - "+group.Nazwa);

        (view.findViewById<ImageButton>(R.id.return_btn)).setOnClickListener {
            it.findNavController().navigate(R.id.action_groupGrades_to_groupDetails)
        }
        (view.findViewById<Button>(R.id.addNewGrade)).setOnClickListener {
            it.findNavController().navigate(R.id.action_groupGrades_to_gradeAdd)
        }
        (view.findViewById<Button>(R.id.gradesList)).setOnClickListener {
            it.findNavController().navigate(R.id.action_groupGrades_to_gradesList2)
        }
    }


}