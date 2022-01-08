package com.example.myapplication.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MainViewModel
import com.example.myapplication.R
import com.example.myapplication.androidVM.todoVM
import com.example.myapplication.data.toDo

class toDoAdapter(val vm: MainViewModel, val toDoVM: todoVM): RecyclerView.Adapter<toDoAdapter.Holder>() {

    private var toDos = emptyList<toDo>();

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tytul: TextView
        val linia1: TextView
        val linia2: TextView
        val usun: ImageButton
        val card: CardView

        init{
            tytul = itemView.findViewById<TextView>(R.id.toDoTitleText)
            linia1 = itemView.findViewById<TextView>(R.id.firstLineText)
            linia2 = itemView.findViewById<TextView>(R.id.secondLineText)
            usun = itemView.findViewById(R.id.deleteBtn)
            card = itemView.findViewById<CardView>(R.id.toDoCard)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.todo,parent,false) as View

        return  Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.tytul.text=toDos[position].Tytul;
        holder.linia1.text=toDos[position].LiniaP;
        holder.linia2.text=toDos[position].LiniaD;

        holder.usun.setOnClickListener{
            toDoVM.deleteToDo(toDos[position])
            notifyDataSetChanged()
        }

        holder.card.setBackgroundColor(Color.parseColor(vm.backgrounds[position%vm.backgrounds.size]));
    }

    fun setData(todos : List<toDo>) {
        this.toDos = todos
        notifyDataSetChanged()
    }

    override fun getItemCount()=toDos.size
}