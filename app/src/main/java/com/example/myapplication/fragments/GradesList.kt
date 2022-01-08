package com.example.myapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Switch
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
import com.example.myapplication.adapters.gradeStudentAdapter
import com.example.myapplication.androidVM.*
import com.example.myapplication.data.student

class gradesList : Fragment() {

    lateinit var LectureVM: lectureVM;
    lateinit var GradeVM: gradeVM;
    lateinit var toDoVM: todoVM;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_grades_list, container, false)

        val viewModel by activityViewModels<MainViewModel>()

        LectureVM = ViewModelProvider(this).get(lectureVM::class.java)
        GradeVM = ViewModelProvider(this).get(gradeVM::class.java)
        toDoVM = ViewModelProvider(this).get(todoVM::class.java)

        var lecture = LectureVM.getList()[viewModel.currentLecture]

        val adapter = gradeListAdapter(viewModel, toDoVM, GradeVM, context)

        val recyclerView = view.findViewById<RecyclerView>(R.id.gradesListRV);
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        GradeVM.getLiveLecture(lecture.id).observe(viewLifecycleOwner, Observer { list ->
            adapter.setData(list)
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel by activityViewModels<MainViewModel>()

        val lecture = LectureVM.getList()[viewModel.currentLecture]

        (view.findViewById<TextView>(R.id.toolbarText)).setText(lecture.Skrot+" - oceny");

        (view.findViewById<ImageButton>(R.id.return_btn)).setOnClickListener {
            it.findNavController().navigate(R.id.action_gradesList2_to_groupGrades)
        }
    }

}