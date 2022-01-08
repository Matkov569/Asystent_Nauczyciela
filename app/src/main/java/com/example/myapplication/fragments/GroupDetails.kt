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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.myapplication.MainViewModel
import com.example.myapplication.R
import com.example.myapplication.androidVM.groupVM
import com.example.myapplication.androidVM.lectureVM
import com.example.myapplication.androidVM.todoVM
import com.example.myapplication.data.toDo

class GroupDetails : Fragment() {

    lateinit var GroupVM:groupVM;
    lateinit var LectureVM:lectureVM;
    lateinit var toDoVM:todoVM;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        GroupVM = ViewModelProvider(this).get(groupVM::class.java)
        LectureVM = ViewModelProvider(this).get(lectureVM::class.java)
        toDoVM = ViewModelProvider(this).get(todoVM::class.java)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_group_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel by activityViewModels<MainViewModel>()

        var lecture = LectureVM.getList()[viewModel.currentLecture]
        var group = GroupVM.getListForLect(lecture.id)[viewModel.currentGroup];


        (view.findViewById<TextView>(R.id.toolbarText)).setText(lecture.Skrot+" - "+group.Nazwa);

        (view.findViewById<ImageButton>(R.id.return_btn)).setOnClickListener {
            it.findNavController().navigate(R.id.action_groupDetails_to_groupListFragment)
        }
        (view.findViewById<Button>(R.id.gradesBtn)).setOnClickListener {
            it.findNavController().navigate(R.id.action_groupDetails_to_groupGrades)
        }
        (view.findViewById<Button>(R.id.groupStudentsList)).setOnClickListener {
            it.findNavController().navigate(R.id.action_groupDetails_to_groupStudents)
        }
        (view.findViewById<Button>(R.id.editGroup)).setOnClickListener {
            viewModel.groupEdit=true;
            it.findNavController().navigate(R.id.action_groupDetails_to_groupAdd)
        }
        (view.findViewById<Button>(R.id.deleteGroup)).setOnClickListener {
            var builder = AlertDialog.Builder(context);
            builder.setTitle("Usuwanie grupy");
            builder.setMessage("Czy na pewno chcesz usunąć grupę?");

            builder.setPositiveButton("Tak") { dialog, which ->
                toDoVM.newToDo(toDo("Usunięto grupę","Przedmiot: "+lecture.Nazwa,"Grupa: "+group.Nazwa))
                GroupVM.delete(group)
                it.findNavController().navigate(R.id.action_groupDetails_to_groupListFragment)
                Toast.makeText(context,"Usunięto grupę", Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("Nie") { dialog, which ->

            }
            builder.show()
        }

    }

}