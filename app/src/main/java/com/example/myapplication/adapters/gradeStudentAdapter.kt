package com.example.myapplication.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MainViewModel
import com.example.myapplication.R
import com.example.myapplication.androidVM.gradeStudVM
import com.example.myapplication.androidVM.todoVM
import com.example.myapplication.data.grade
import com.example.myapplication.data.gradeStudent
import com.example.myapplication.data.student
import com.example.myapplication.data.toDo
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class gradeStudentAdapter(
    val vm: MainViewModel,
    val GSVM: gradeStudVM,
    val toDoVM: todoVM,
    val context: Context?,
    val stud: student,
    val lect:String
    ): RecyclerView.Adapter<gradeStudentAdapter.Holder>() {

    private var objs = emptyList<grade>()
    private var grades = emptyList<gradeStudent>()

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        val Nazwa: TextView
        val Ocena: EditText
        val Max: TextView
        val card: CardView

        init{
            Nazwa = itemView.findViewById<TextView>(R.id.SGcardTxt)
            Ocena = itemView.findViewById<EditText>(R.id.SGcardNumber)
            Max = itemView.findViewById<TextView>(R.id.SGcardMax)
            card = itemView.findViewById<CardView>(R.id.SGcard)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.studentgradecard,parent,false) as View

        return  Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.Nazwa.text=objs[position].Nazwa;
        holder.Max.text=objs[position].Max.toString();
        var index = grades.indexOf(grades.find { it.grade == objs[position].id});

        var grade:gradeStudent;

        if( index != -1) {
            holder.Ocena.setText(grades[index].mark.toString());
            grade = grades[index]
        }
        else{
            grade = gradeStudent(objs[position].id,stud.id,0.0)
        }
        holder.card.setBackgroundColor(Color.parseColor(vm.backgrounds[position%vm.backgrounds.size]));

        holder.Ocena.setOnFocusChangeListener{
            view, focus ->
            if(!focus){
                if(holder.Ocena.text.toString()!="") {
                    if (holder.Ocena.text.toString().toDouble() <= objs[position].Max
                        && holder.Ocena.text.toString().toDouble() >= 0
                    ) {
                        if (index != -1)
                            toDoVM.newToDo(
                                toDo(
                                    "Nowa ocena",
                                    "Przedmiot: " + lect + "\nOcena: " + objs[position].Nazwa,
                                    "Student: " + stud.Nazwisko + " " + stud.Imie + "\nWartość: " + holder.Ocena.text.toString()
                                )
                            )
                        else
                            toDoVM.newToDo(
                                toDo(
                                    "Zmiana oceny",
                                    "Przedmiot: " + lect + "\nOcena: " + objs[position].Nazwa,
                                    "Student: " + stud.Nazwisko + " " + stud.Imie + "\nNowa wartość: " + holder.Ocena.text.toString()
                                )
                            )

                        grade.mark = holder.Ocena.text.toString().toDouble();
                        runBlocking {
                            launch {
                                delay(0)

                                GSVM.insert(grade)
                            }
                        }
                    }
                    else {
                        Toast.makeText(context, "Wprowadź poprawną wartość!", Toast.LENGTH_LONG)
                            .show()
                    }
                }
                else {
                    Toast.makeText(context, "Wprowadź poprawną wartość!", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    fun setData(obj:List<grade>, grades:List<gradeStudent>){
        this.objs = obj;
        this.grades = grades;
        notifyDataSetChanged()
    }

    override fun getItemCount()=objs.size
}