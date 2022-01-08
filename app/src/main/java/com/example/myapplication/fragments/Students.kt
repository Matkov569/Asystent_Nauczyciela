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
import com.example.myapplication.adapters.lectureAdapter
import com.example.myapplication.adapters.studentAdapter
import com.example.myapplication.androidVM.*
import com.example.myapplication.data.toDo
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Students : Fragment() {

    lateinit var StudentVM:studentVM;
    lateinit var toDoVM:todoVM;
    lateinit var StudLectVM:studlectVM;
    lateinit var GroupStudVM:groupStudVM;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_students, container, false)
        // = ViewModelProvider(requireActivity()).get(lectureVM::class.java);
        val viewModel by activityViewModels<MainViewModel>()
        StudentVM = ViewModelProvider(this).get(studentVM::class.java)
        toDoVM = ViewModelProvider(this).get(todoVM::class.java)
        StudLectVM = ViewModelProvider(this).get(studlectVM::class.java)
        GroupStudVM = ViewModelProvider(this).get(groupStudVM::class.java)

        val adapter = studentAdapter(viewModel,toDoVM,StudentVM,context)
        val recyclerView = view.findViewById<RecyclerView>(R.id.settStudentListRV);
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        StudentVM.students.observe(viewLifecycleOwner, Observer { student ->
            adapter.setData(student)
        })

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel by activityViewModels<MainViewModel>()


        (view.findViewById<TextView>(R.id.toolbarText)).setText("Lista studentów");

        (view.findViewById<ImageButton>(R.id.return_btn)).setOnClickListener {
            it.findNavController().navigate(R.id.action_students_to_settings)
        }

        (view.findViewById<Button>(R.id.newStudentBtn)).setOnClickListener {
            it.findNavController().navigate(R.id.action_students_to_editStudent)
        }

        (view.findViewById<Button>(R.id.blipStudents)).setOnClickListener {
            var builder = AlertDialog.Builder(context);
            builder.setTitle("Usuwanie wszystkich studentów");
            builder.setMessage("Czy na pewno chcesz usunąć wszystkich studentów?");

            builder.setPositiveButton("Tak") { dialog, which ->
                toDoVM.newToDo(toDo("Usunięto wszystkich studentów"))
                runBlocking {
                    launch {
                        delay(100)
                        Toast.makeText(context,"Usunięto wszystkich studentów", Toast.LENGTH_SHORT).show()
                    }
                    launch {
                        delay(0)
                        StudentVM.getList().forEach {
                            //ocena student
                            //grupa student
                            GroupStudVM.deleteStud(it.id)
                            //przedmiot student
                            StudLectVM.deleteStud(it.id)
                            //student
                            StudentVM.deleteStudent(it);
                        }
                    }
                }
            }
            builder.setNegativeButton("Nie") { dialog, which ->

            }
            builder.show()
        }
    }
}