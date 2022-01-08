package com.example.myapplication.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.myapplication.MainViewModel
import com.example.myapplication.R
import com.example.myapplication.androidVM.*
import com.example.myapplication.data.grade
import com.example.myapplication.data.toDo
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class gradeAdd : Fragment() {

    lateinit var LectureVM: lectureVM;
    lateinit var GradeVM: gradeVM;
    lateinit var toDoVM: todoVM;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        LectureVM = ViewModelProvider(this).get(lectureVM::class.java)
        GradeVM = ViewModelProvider(this).get(gradeVM::class.java)
        toDoVM = ViewModelProvider(this).get(todoVM::class.java)

        return inflater.inflate(R.layout.fragment_grade_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel by activityViewModels<MainViewModel>()

        val lecture = LectureVM.getList()[viewModel.currentLecture]

        if(viewModel.gradeEdit){
            var grade = GradeVM.getGrade(viewModel.currentGrade)
            (view.findViewById<TextView>(R.id.gradeNameText)).setText(grade.Nazwa);
            (view.findViewById<TextView>(R.id.gradeMaxInput)).setText(grade.Max.toString());
            (view.findViewById<Switch>(R.id.gradeRequirement)).isChecked=grade.Wymagana;
            (view.findViewById<TextView>(R.id.toolbarText)).setText("Edycja oceny");

            (view.findViewById<ImageButton>(R.id.return_btn)).setOnClickListener {
                it.findNavController().navigate(R.id.action_gradeAdd_to_gradesList2)
            }
        }
        else {
            (view.findViewById<TextView>(R.id.toolbarText)).setText("Nowa ocena");
            (view.findViewById<ImageButton>(R.id.return_btn)).setOnClickListener {
                it.findNavController().navigate(R.id.action_gradeAdd_to_groupGrades)
            }
        }



        (view.findViewById<Button>(R.id.addGradeSaveBtn)).setOnClickListener {
            var name = (view.findViewById<TextView>(R.id.gradeNameText)).text.toString();
            var max = (view.findViewById<TextView>(R.id.gradeMaxInput)).text.toString();
            var require = (view.findViewById<Switch>(R.id.gradeRequirement)).isChecked;
            if(name == "" || max == ""){
                Toast.makeText(context,"Uzupełnij wszystkie pola!",Toast.LENGTH_SHORT).show()
            }
            else{
                if(viewModel.gradeEdit){
                    viewModel.gradeEdit = false;
                    var grade = GradeVM.getGrade(viewModel.currentGrade)
                    toDoVM.newToDo(toDo("Edycja oceny","Edycja oceny: "+grade.Nazwa))

                    runBlocking {
                        launch {
                            delay(0)
                            grade.Nazwa = name;
                            grade.Max=max.toInt()
                            grade.Wymagana=require;
                            GradeVM.update(grade);
                        }
                        launch {
                            delay(100)
                            it.findNavController().navigate(R.id.action_gradeAdd_to_gradesList2)
                        }
                    }
                }
                else{
                    toDoVM.newToDo(toDo("Nowa ocena","Przedmiot: "+lecture.Nazwa,"Ocena: "+name))
                    var grade = grade(name,max.toInt(),require,lecture.id)
                    runBlocking {
                        launch {
                            delay(0)
                            GradeVM.newGrade(grade);
                        }
                        launch {
                            delay(100)
                            it.findNavController().navigate(R.id.action_gradeAdd_to_groupGrades)
                        }
                    }
                }
            }
        }
    }
}