package com.example.myapplication.adapters

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MainViewModel
import com.example.myapplication.R
import com.example.myapplication.androidVM.studentVM
import com.example.myapplication.androidVM.studlectVM
import com.example.myapplication.androidVM.todoVM
import com.example.myapplication.data.student
import com.example.myapplication.data.toDo

class studentAdapter(val vm: MainViewModel, val toDoVM: todoVM, val StudentVM:studentVM, var context: Context?): RecyclerView.Adapter<studentAdapter.Holder>() {

    private var objs = emptyList<student>();

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        val Nazwa: TextView
        val Album: TextView
        val card: CardView
        val del: ImageButton

        init{
            Nazwa= itemView.findViewById<TextView>(R.id.firstLineText)
            Album=itemView.findViewById<TextView>(R.id.secondLineText)
            card = itemView.findViewById(R.id.toDoCard)
            del = itemView.findViewById(R.id.deleteBtn)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.student,parent,false) as View

        return  Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.Nazwa.text=objs[position].Nazwisko + " " + objs[position].Imie;
        holder.Album.text=objs[position].Album.toString();

        holder.card.setBackgroundColor(Color.parseColor(vm.backgrounds[position%vm.backgrounds.size]));
        holder.itemView.setOnClickListener{
            vm.studentEdit = true;
            vm.actStudent(position)
            it.findNavController().navigate(R.id.action_students_to_editStudent)
        }
        holder.del.setOnClickListener{

            var builder = AlertDialog.Builder(context);
            builder.setTitle("Usuwanie studenta");
            builder.setMessage("Czy na pewno chcesz usunąć studenta?");

            builder.setPositiveButton("Tak") { dialog, which ->
                toDoVM.newToDo(toDo(
                    "Usunięto studenta",
                    "Usunięty student:",
                    objs[position].Nazwisko + " " + objs[position].Imie + " - "+objs[position].Album.toString()
                ))
                StudentVM.removeStudent(objs[position])
                notifyDataSetChanged()
                Toast.makeText(context,"Usunięto studenta", Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("Nie") { dialog, which ->

            }
            builder.show()


        }
    }

    fun setData(objs : List<student>) {
        this.objs = objs
        notifyDataSetChanged()
    }

    override fun getItemCount()=objs.size

}