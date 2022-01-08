package com.example.myapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MainViewModel
import com.example.myapplication.R
import com.example.myapplication.adapters.*
import com.example.myapplication.adapters.studentAdapter
import com.example.myapplication.androidVM.*
import com.example.myapplication.androidVM.studentVM
import com.example.myapplication.androidVM.studlectVM
import com.example.myapplication.data.*
import com.example.myapplication.data.toDo
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class EditStudent : Fragment() {

    lateinit var StudLectVM: studlectVM;
    lateinit var LectureVM:lectureVM;
    lateinit var StudentVM:studentVM;
    lateinit var toDoVM:todoVM;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_student, container, false)
        // = ViewModelProvider(requireActivity()).get(lectureVM::class.java);
        val viewModel by activityViewModels<MainViewModel>()
        StudLectVM = ViewModelProvider(this).get(studlectVM::class.java)
        StudentVM = ViewModelProvider(this).get(studentVM::class.java)
        toDoVM = ViewModelProvider(this).get(todoVM::class.java)

        if(viewModel.studentEdit){
            println(StudentVM.getList().toString())
            println(StudentVM.getList()[viewModel.currentStudent].id.toString())
            viewModel.selectedLectures = StudLectVM.getForStud(StudentVM.getList()[viewModel.currentStudent].id);
        }

        val adapter = lectureOptAdapter(viewModel)
        val recyclerView = view.findViewById<RecyclerView>(R.id.studentsLecturesList);
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        LectureVM = ViewModelProvider(this).get(lectureVM::class.java)
        LectureVM.lectures.observe(viewLifecycleOwner, Observer { student ->
            adapter.setData(student)
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel by activityViewModels<MainViewModel>()

        println(StudLectVM.getAllStudLects().toString())
        println(StudLectVM.getForStud(1L).toString())


        if(viewModel.studentEdit){
            (view.findViewById<TextView>(R.id.toolbarText)).setText("Edycja studenta");
            var actStud = StudentVM.getList()[viewModel.currentStudent];
            (view.findViewById<TextView>(R.id.studentsFirstName)).setText(actStud.Imie);
            (view.findViewById<TextView>(R.id.studentsLastName)).setText(actStud.Nazwisko);
            (view.findViewById<TextView>(R.id.studentsAlbumNumber)).setText(actStud.Album.toString());
        }
        else
            (view.findViewById<TextView>(R.id.toolbarText)).setText("Nowy student");

        (view.findViewById<ImageButton>(R.id.return_btn)).setOnClickListener {
            viewModel.studentEdit=false;
            viewModel.selectedLectures.clear();
            it.findNavController().navigate(R.id.action_editStudent_to_students)
        }

        (view.findViewById<Button>(R.id.saveStudentBtn)).setOnClickListener {
            var Name = view.findViewById<EditText>(R.id.studentsFirstName).text.toString();
            var LastName = view.findViewById<EditText>(R.id.studentsLastName).text.toString();
            var Album = view.findViewById<EditText>(R.id.studentsAlbumNumber).text.toString();

            if(Name == "" || LastName == "" || Album == ""){
                Toast.makeText(context,"Uzupełnij wszystkie pola!",Toast.LENGTH_LONG).show()
            }
            else{
                if(viewModel.studentEdit) {
                    viewModel.studentEdit = false;

                    var lect = StudentVM.getList()[viewModel.currentStudent];
                    //zmiana danych
                    if(lect.Imie != Name || lect.Nazwisko != LastName || lect.Nazwisko != LastName) {
                        toDoVM.newToDo(
                            toDo(
                            "Edycja danych studenta",
                            lect.Nazwisko + " " + lect.Imie + " - " + lect.Album,
                            Name + " " + LastName + " - " + Album
                        ))
                        lect.Imie = Name
                        lect.Nazwisko = LastName
                        lect.Album = Album.toInt()
                        StudentVM.updateStudent(lect)
                    }
                    //zmiana przedmiotów
                    toDoVM.newToDo(
                        toDo(
                            "Edycja przedmiotów studenta",
                            "Student: ",
                            lect.Nazwisko + " " + lect.Imie + " - " + lect.Album
                        )
                    )

                    runBlocking {
                        launch {
                            delay(50)
                            viewModel.selectedLectures.forEach {
                                StudLectVM.insertStudLect(studentLecture(it,lect.id))
                            }
                        }
                        launch {
                            delay(100)
                            viewModel.selectedLectures.clear();
                            it.findNavController().navigate(R.id.action_editStudent_to_students)
                        }
                        StudLectVM.deleteStud(lect.id)
                    }
                }
                else{
                    var index = StudentVM.newStudent(student(Name,LastName,Album.toInt()))
                    toDoVM.newToDo(toDo("Nowy Student","Utworzono studenta",Name+" "+LastName+" - "+Album))
                    runBlocking {
                        launch {
                            delay(50)
                            viewModel.selectedLectures.forEach {
                                StudLectVM.insertStudLect(studentLecture(it,index))
                            }
                        }
                        launch {
                            delay(100)
                            viewModel.selectedLectures.clear();
                            it.findNavController().navigate(R.id.action_editStudent_to_students)
                        }
                        StudLectVM.deleteStud(index)
                    }
                }
            }

        }
        /*runBlocking {
            launch {
                delay(50)
                viewModel.selectedStudents.forEach {
                    GroupStudVM.insert(groupStudent(group.id,it))
                }
            }
            launch {
                delay(100)
                viewModel.selectedLectures.clear();
                it.findNavController().navigate(R.id.action_editStudent_to_students)
            }
            GroupStudVM.deleteGroup(group.id)
        }*/
    }
}