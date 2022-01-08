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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MainViewModel
import com.example.myapplication.R
import com.example.myapplication.adapters.lectureOptAdapter
import com.example.myapplication.androidVM.*
import com.example.myapplication.data.group
import com.example.myapplication.data.lecture
import com.example.myapplication.data.toDo

class groupAdd : Fragment() {

    lateinit var StudLectVM: studlectVM;
    lateinit var LectureVM: lectureVM;
    lateinit var StudentVM: studentVM;
    lateinit var GroupVM: groupVM;
    lateinit var toDoVM: todoVM;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_group_add, container, false)
        // = ViewModelProvider(requireActivity()).get(lectureVM::class.java);
        toDoVM = ViewModelProvider(this).get(todoVM::class.java)
        LectureVM = ViewModelProvider(this).get(lectureVM::class.java)
        GroupVM = ViewModelProvider(this).get(groupVM::class.java)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel by activityViewModels<MainViewModel>()

        if(viewModel.groupEdit){
            var lecture = LectureVM.getList()[viewModel.currentLecture]
            var group = GroupVM.getListForLect(lecture.id)[viewModel.currentGroup];
            (view.findViewById<TextView>(R.id.toolbarText)).setText("Edycja grupy");
            (view.findViewById<TextView>(R.id.groupEditNameText)).setText(group.Nazwa);
            (view.findViewById<TextView>(R.id.groupEditDateText)).setText(group.Data);
        }
        else
            (view.findViewById<TextView>(R.id.toolbarText)).setText("Nowa grupa");

        (view.findViewById<ImageButton>(R.id.return_btn)).setOnClickListener {
            viewModel.groupEdit=false;
            it.findNavController().navigate(R.id.action_groupAdd_to_groupListFragment)
        }

        (view.findViewById<Button>(R.id.groupEditSaveBtn)).setOnClickListener {
            var Name = view.findViewById<EditText>(R.id.groupEditNameText).text.toString();
            var Date = view.findViewById<EditText>(R.id.groupEditDateText).text.toString();

            if(Name == "" || Date == ""){
                Toast.makeText(context,"Uzupełnij wszystkie pola!", Toast.LENGTH_LONG).show()
            }
            else{
                if(viewModel.groupEdit) {
                    var lecture = LectureVM.getList()[viewModel.currentLecture]
                    var lect = GroupVM.getListForLect(lecture.id)[viewModel.currentGroup];
                    if(lect.Nazwa != Name || lect.Data != Date) {
                        toDoVM.newToDo(
                            toDo(
                            "Edycja grupy",
                            "Zmodyfikowana grupa:",
                            lect.Nazwa+" ("+lect.Data+") -> "+Name+" ("+Date+")"
                        )
                        )
                        lect.Nazwa = Name
                        lect.Data = Date
                        GroupVM.update(lect)
                    }
                }
                else{
                    var index = GroupVM.newGroup(group(Name,Date,LectureVM.getList()[viewModel.currentLecture].id))
                    var ind = GroupVM.getList().indexOf(GroupVM.getList().find{ it.id == index });
                    viewModel.actGroup(ind)

                    toDoVM.newToDo(toDo("Nowa grupa","Utworzono grupę dla "+LectureVM.getList()[viewModel.currentLecture].Skrot,Name+" ("+Date+")"))
                }
                it.findNavController().navigate(R.id.action_groupAdd_to_groupListFragment)
            }
        }

    }
}