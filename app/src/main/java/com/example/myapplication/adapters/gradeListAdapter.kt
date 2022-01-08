package com.example.myapplication.adapters

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MainViewModel
import com.example.myapplication.R
import com.example.myapplication.androidVM.gradeVM
import com.example.myapplication.androidVM.studentVM
import com.example.myapplication.androidVM.todoVM
import com.example.myapplication.data.grade
import com.example.myapplication.data.student
import com.example.myapplication.data.toDo

class gradeListAdapter(val vm: MainViewModel, val toDoVM: todoVM, val GradeVM: gradeVM, var context: Context?): RecyclerView.Adapter<gradeListAdapter.Holder>() {

    private var objs = emptyList<grade>();

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        val Nazwa: TextView
        val Max: TextView
        val Wymog: TextView
        val card: CardView
        val del: ImageButton
        val edit: ImageButton

        init{
            Nazwa = itemView.findViewById<TextView>(R.id.gradeCardNameText)
            Max = itemView.findViewById<TextView>(R.id.gradeCardMaxText)
            Wymog = itemView.findViewById<TextView>(R.id.gradeReqText)
            card = itemView.findViewById(R.id.gradeCard)
            del = itemView.findViewById(R.id.delBtn)
            edit = itemView.findViewById(R.id.editBtn)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.gradecard,parent,false) as View

        return  Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.Nazwa.text=objs[position].Nazwa;
        holder.Max.text="Max: "+objs[position].Max.toString();
        holder.Wymog.text = if(objs[position].Wymagana) "Wymagana" else "Niewymagana";

        holder.card.setBackgroundColor(Color.parseColor(vm.backgrounds[position%vm.backgrounds.size]));

        holder.itemView.setOnClickListener{
            vm.actGrade(objs[position].id)
            it.findNavController().navigate(R.id.action_gradesList2_to_gradesDetail)
        }

        holder.edit.setOnClickListener{
            vm.gradeEdit = true;
            vm.actGrade(objs[position].id)
            it.findNavController().navigate(R.id.action_gradesList2_to_gradeAdd)
        }

        holder.del.setOnClickListener{

            var builder = AlertDialog.Builder(context);
            builder.setTitle("Usuwanie oceny");
            builder.setMessage("Czy na pewno chcesz usunąć ocenę?");

            builder.setPositiveButton("Tak") { dialog, which ->
                toDoVM.newToDo(
                    toDo(
                    "Usunięto ocenę",
                    "Przedmiot: ",
                    "Ocena: "+objs[position].Nazwa
                )
                )
                GradeVM.removeGrade(objs[position])
                notifyDataSetChanged()
                Toast.makeText(context,"Usunięto ocenę", Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("Nie") { dialog, which ->

            }
            builder.show()


        }
    }

    fun setData(objs : List<grade>) {
        this.objs = objs
        notifyDataSetChanged()
    }

    override fun getItemCount()=objs.size

}