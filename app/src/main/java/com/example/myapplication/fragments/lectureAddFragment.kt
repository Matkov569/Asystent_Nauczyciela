package com.example.myapplication.fragments

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
import com.example.myapplication.androidVM.lectureVM
import com.example.myapplication.androidVM.todoVM
import com.example.myapplication.data.lecture
import com.example.myapplication.data.toDo

class lectureAddFragment : Fragment() {

    lateinit var toDoVM: todoVM;
    lateinit var LectureVM: lectureVM;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        toDoVM = ViewModelProvider(requireActivity()).get(todoVM::class.java);
        LectureVM = ViewModelProvider(requireActivity()).get(lectureVM::class.java);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lecture_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel by activityViewModels<MainViewModel>()

        if(viewModel.lectureEdit) {
            var actLect = LectureVM.getList()[viewModel.currentLecture];
            (view.findViewById<TextView>(R.id.toolbarText)).setText("Edycja przedmiotu")
            view.findViewById<EditText>(R.id.inputFullName).setText(actLect.Nazwa);
            view.findViewById<EditText>(R.id.inputShortName).setText(actLect.Skrot);
        }
        else
            (view.findViewById<TextView>(R.id.toolbarText)).setText("Nowy przedmiot");

        (view.findViewById<ImageButton>(R.id.return_btn)).setOnClickListener {
            if(viewModel.lectureEdit) {
                viewModel.lectureEdit = false;
                it.findNavController().navigate(R.id.action_lectureAddFragment_to_groupListFragment)
            }
            else
                it.findNavController().navigate(R.id.action_lectureAddFragment_to_welcomeFragment2)
        }

        (view.findViewById<Button>(R.id.addLectureBtn)).setOnClickListener {
            var longName = view.findViewById<EditText>(R.id.inputFullName).text.toString();
            var shortName = view.findViewById<EditText>(R.id.inputShortName).text.toString();

            if(longName == "" || shortName == ""){
                Toast.makeText(context,"Uzupełnij wszystkie pola!",Toast.LENGTH_LONG).show()
            }
            else{
                if(viewModel.lectureEdit) {
                    var lect = LectureVM.getList()[viewModel.currentLecture];
                    if(lect.Nazwa != longName || lect.Skrot != shortName) {
                        toDoVM.newToDo(toDo(
                            "Edycja przedmiotu",
                            "Zmodyfikowany przedmiot:",
                            lect.Nazwa+" ("+lect.Skrot+") -> "+longName+" ("+shortName+")"
                        ))
                        lect.Nazwa = longName
                        lect.Skrot = shortName
                        LectureVM.updateLecture(lect)
                    }
                }
                else{
                    var index = LectureVM.newLecture(lecture(longName,shortName))
                    println(index);
                    var ind = LectureVM.getList().indexOf(LectureVM.getList().find{ it.id == index });
                    println(ind);
                    viewModel.actLecture(ind)

                    toDoVM.newToDo(toDo("Nowy przedmiot","Utworzono przedmiot",longName+" ("+shortName+")"))
                }
                it.findNavController().navigate(R.id.action_lectureAddFragment_to_groupListFragment)
            }

        }

    }
}