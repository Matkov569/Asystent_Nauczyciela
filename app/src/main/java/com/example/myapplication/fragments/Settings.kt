package com.example.myapplication.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.myapplication.R
import android.content.DialogInterface
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.androidVM.userVM

import com.example.myapplication.data.toDo
import com.example.myapplication.data.user
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class Settings : Fragment() {

    lateinit var UserVM: userVM;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        UserVM = ViewModelProvider(this).get(userVM::class.java)

        return inflater.inflate(R.layout.fragment_settings, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (view.findViewById<TextView>(R.id.toolbarText)).setText("Ustawienia");

        (view.findViewById<ImageButton>(R.id.return_btn)).setOnClickListener {
            it.findNavController().navigate(R.id.action_settings_to_welcomeFragment2)
        }

        view.findViewById<TextView>(R.id.editTextTextPersonName).setText(UserVM.getUser()[0].user)

        (view.findViewById<Button>(R.id.saveBtn)).setOnClickListener {
            var name = view.findViewById<TextView>(R.id.editTextTextPersonName).text.toString();
            var user = UserVM.getUser()[0]
            user.user = name
            UserVM.user(user);
            Toast.makeText(context,"Zapisano",Toast.LENGTH_LONG).show()
        }

        (view.findViewById<Button>(R.id.goStudents)).setOnClickListener {
            it.findNavController().navigate(R.id.action_settings_to_students)
        }
        (view.findViewById<Button>(R.id.blipBtn)).setOnClickListener {
            var builder = AlertDialog.Builder(context);
            builder.setTitle("Kompletne czyszczenie");
            builder.setMessage("Czy na pewno chcesz usunąć wszystkie dane?\nTa operacja jest nieodwracalna!");
            builder.setPositiveButton("Tak") { dialog, which ->
                runBlocking {
                    launch {
                        delay(0)
                        UserVM.deleteAll()
                    }
                    launch {
                        delay(100)
                        Toast.makeText(context,"Usunięto wszystkie dane",Toast.LENGTH_LONG).show()
                    }
                }
            }
            builder.setNegativeButton("Nie") { dialog, which ->

            }
            builder.show()
        }
    }
}