package com.example.myapplication.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MainViewModel
import com.example.myapplication.R
import com.example.myapplication.data.student


class studentsGradesAdapter(val vm: MainViewModel): RecyclerView.Adapter<studentsGradesAdapter.Holder>() {

    private var objs = emptyList<student>()
    private var grades = emptyList<String>()

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        val NumerNaLiscie: TextView
        val NazwiskoIImie: TextView
        val IloscOcen: TextView
        val card: CardView

        init{
            NumerNaLiscie = itemView.findViewById<TextView>(R.id.studentNumberText)
            NazwiskoIImie = itemView.findViewById<TextView>(R.id.studentName)
            IloscOcen = itemView.findViewById<TextView>(R.id.studentGrades)
            card = itemView.findViewById<CardView>(R.id.gradesCard)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.studentsgradescard,parent,false) as View

        return  Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.NumerNaLiscie.text=(position+1).toString();
        holder.NazwiskoIImie.text= objs[position].Nazwisko + " " + objs[position].Imie;
        holder.IloscOcen.text=grades[position];
        holder.card.setBackgroundColor(Color.parseColor(vm.backgrounds[position%vm.backgrounds.size]));
        holder.card.setOnClickListener{
            vm.actStudent(position)
            it.findNavController().navigate(R.id.action_groupGrades_to_studentsGrades)
        }
    }

    fun setData(obj:List<student>, grades:List<String>){
        this.objs = obj;
        this.grades = grades;
        notifyDataSetChanged()
    }

    override fun getItemCount()=objs.size
}