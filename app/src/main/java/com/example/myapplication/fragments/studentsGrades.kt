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
import com.example.myapplication.adapters.gradeStudentAdapter
import com.example.myapplication.adapters.studentsGradesAdapter
import com.example.myapplication.androidVM.*
import com.example.myapplication.data.grade
import com.example.myapplication.data.student

class studentsGrades : Fragment() {

    lateinit var StudLectVM: studlectVM;
    lateinit var GroupStudVM: groupStudVM;
    lateinit var GradeStudVM: gradeStudVM;
    lateinit var LectureVM: lectureVM;
    lateinit var GradeVM: gradeVM;
    lateinit var GroupVM: groupVM;
    lateinit var toDoVM: todoVM;
    lateinit var StudentVM: studentVM;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_students_grades, container, false)
        val viewModel by activityViewModels<MainViewModel>()

        LectureVM = ViewModelProvider(this).get(lectureVM::class.java)
        GradeVM = ViewModelProvider(this).get(gradeVM::class.java)
        GroupVM = ViewModelProvider(this).get(groupVM::class.java)
        StudLectVM = ViewModelProvider(this).get(studlectVM::class.java)
        toDoVM = ViewModelProvider(this).get(todoVM::class.java)
        StudentVM = ViewModelProvider(this).get(studentVM::class.java)
        GroupStudVM = ViewModelProvider(this).get(groupStudVM::class.java)
        GradeStudVM = ViewModelProvider(this).get(gradeStudVM::class.java)

        var lecture = LectureVM.getList()[viewModel.currentLecture]
        var group = GroupVM.getListForLect(lecture.id)[viewModel.currentGroup]
        var students = GroupStudVM.getForGroup(group.id)
        var student = StudentVM.getStudent(students[viewModel.currentStudent])

        var grades = GradeStudVM.getForStud(students[viewModel.currentStudent])

        val adapter = gradeStudentAdapter(viewModel, GradeStudVM, toDoVM, context, student,lecture.Skrot)
        val recyclerView = view.findViewById<RecyclerView>(R.id.SGcardRV);
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        GradeVM.getLiveLecture(lecture.id).observe(viewLifecycleOwner, Observer { list ->
            adapter.setData(list,grades)
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel by activityViewModels<MainViewModel>()

        var lecture = LectureVM.getList()[viewModel.currentLecture];
        var group = GroupVM.getListForLect(lecture.id)[viewModel.currentGroup];
        var id = GroupStudVM.getForGroup(group.id)[viewModel.currentStudent];
        var student = StudentVM.getStudent(id);
        var grades = GradeStudVM.getForStud(id);

        (view.findViewById<TextView>(R.id.toolbarText)).setText(student.Nazwisko+" "+student.Imie);

        (view.findViewById<ImageButton>(R.id.return_btn)).setOnClickListener {
            it.findNavController().navigate(R.id.action_studentsGrades_to_groupGrades)
        }


    }
}