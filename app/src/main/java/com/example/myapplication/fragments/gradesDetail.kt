package com.example.myapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.myapplication.adapters.gradeListAdapter
import com.example.myapplication.adapters.studentGradeAdapter
import com.example.myapplication.androidVM.*
import com.example.myapplication.data.grade
import com.example.myapplication.data.gradeStudent
import com.example.myapplication.data.student

class gradesDetail : Fragment() {

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
        var view = inflater.inflate(R.layout.fragment_grades_detail, container, false)

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
        var studs = GroupStudVM.getForGroup(group.id)

        if(studs.size>0) {
            //ocena (kryterium)
            var grade = GradeVM.getGrade(viewModel.currentGrade)

            //oceny studentów
            var marks = GradeStudVM.getForGrade(viewModel.currentGrade)

            val adapter = studentGradeAdapter(viewModel, GradeStudVM, toDoVM, context, grade, lecture.Skrot)

            val recyclerView = view.findViewById<RecyclerView>(R.id.SGcardRV);
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())


            GroupStudVM.getGroup(group.id).observe(viewLifecycleOwner, Observer { group ->
                adapter.setData(group, marks)
            })
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel by activityViewModels<MainViewModel>()

        val lecture = LectureVM.getList()[viewModel.currentLecture]
        var group = GroupVM.getListForLect(lecture.id)[viewModel.currentGroup]

        (view.findViewById<TextView>(R.id.toolbarText)).setText(lecture.Skrot+" - "+group.Nazwa);

        (view.findViewById<ImageButton>(R.id.return_btn)).setOnClickListener {
            it.findNavController().navigate(R.id.action_gradesDetail_to_gradesList2)
        }
    }
}