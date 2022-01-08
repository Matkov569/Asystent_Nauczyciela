package com.example.myapplication.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MainViewModel
import com.example.myapplication.R
import com.example.myapplication.adapters.lectureAdapter
import com.example.myapplication.androidVM.groupVM
import com.example.myapplication.androidVM.lectureVM
import com.example.myapplication.androidVM.userVM
import com.example.myapplication.data.lecture
import com.example.myapplication.data.user

class WelcomeFragment : Fragment() {

    lateinit var LectureVM: lectureVM;
    lateinit var GroupVM: groupVM;
    lateinit var UserVM: userVM;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_welcome,container,false);
        // = ViewModelProvider(requireActivity()).get(lectureVM::class.java);
        val viewModel by activityViewModels<MainViewModel>()
        GroupVM = ViewModelProvider(this).get(groupVM::class.java)
        UserVM = ViewModelProvider(this).get(userVM::class.java)

        var user = UserVM.getUser()
        if(user.size==0){
            var builder = AlertDialog.Builder(context);
            builder.setTitle("Witaj");
            builder.setMessage("Podaj nazwę użytkownika:");
            val input = EditText(context)
            builder.setView(input)
            builder.setPositiveButton("Ok") { dialog, which ->
                var name = input.text.toString();
                UserVM.user(user(name))
            }
            builder.show()
        }

        val adapter = lectureAdapter(viewModel,GroupVM)
        val recyclerView = view.findViewById<RecyclerView>(R.id.lecturesRV);
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        LectureVM = ViewModelProvider(this).get(lectureVM::class.java)
        LectureVM.lectures.observe(viewLifecycleOwner, Observer { lecture ->
            adapter.setData(lecture)
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel by activityViewModels<MainViewModel>()
        var user = UserVM.getUser()

        var name = "";
        if(user.size==1)
            name = user[0].user

        (view.findViewById<ImageButton>(R.id.return_btn)).visibility=View.GONE;
        (view.findViewById<TextView>(R.id.toolbarText)).setText("Witaj "+ name);

        (view.findViewById<Button>(R.id.settingsBtn)).setOnClickListener {
            it.findNavController().navigate(R.id.action_welcomeFragment2_to_settings)
        }

        (view.findViewById<Button>(R.id.newLectureBtn)).setOnClickListener {
            it.findNavController().navigate(R.id.action_welcomeFragment2_to_lectureAddFragment)
        }

        (view.findViewById<Button>(R.id.raportInfoBtn)).setOnClickListener {
            it.findNavController().navigate(R.id.action_welcomeFragment2_to_shortcut2)
        }
    }
}