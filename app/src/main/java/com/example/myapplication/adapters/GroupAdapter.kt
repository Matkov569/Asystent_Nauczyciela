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
import com.example.myapplication.androidVM.groupStudVM
import com.example.myapplication.data.group
import com.example.myapplication.data.groupStudent
import com.example.myapplication.data.lecture
import com.example.myapplication.data.student

class groupAdapter(val vm: MainViewModel):RecyclerView.Adapter<groupAdapter.Holder>() {

    private var objs = emptyList<group>();
    private var counts = emptyList<Int>();

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        val Nazwa: TextView;
        val Data: TextView;
        val Liczba: TextView;
        val card:CardView;
        //val CzasZajec: TextView

        init{
            Nazwa= itemView.findViewById<TextView>(R.id.groupName)
            Data=itemView.findViewById<TextView>(R.id.groupDate)
            Liczba=itemView.findViewById<TextView>(R.id.studentsCount)
            card=itemView.findViewById<CardView>(R.id.groupCardCard)


            itemView.setOnClickListener{
                it.findNavController().navigate(R.id.action_groupListFragment_to_groupDetails)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.groupcard,parent,false) as View

        return  Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.Nazwa.text=objs[position].Nazwa;
        holder.Data.text=objs[position].Data;
        holder.Liczba.text="Liczba studentów: "+counts[position];
        holder.itemView.setOnClickListener {
            vm.actGroup(position)
            it.findNavController().navigate(R.id.action_groupListFragment_to_groupDetails)
        }
        holder.card.setBackgroundColor(Color.parseColor(vm.backgrounds[position%vm.backgrounds.size]));
    }

    fun setData(obj : List<group>, counts: List<Int>) {
        this.objs = obj
        this.counts = counts
        notifyDataSetChanged()
    }

    override fun getItemCount()=objs.size

}