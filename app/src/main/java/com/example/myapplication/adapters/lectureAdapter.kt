package com.example.myapplication.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MainViewModel
import com.example.myapplication.R
import com.example.myapplication.androidVM.groupVM
import com.example.myapplication.androidVM.lectureVM
import com.example.myapplication.data.lecture

class lectureAdapter(val vm: MainViewModel,val GroupVM:groupVM):RecyclerView.Adapter<lectureAdapter.Holder>() {

    private var lectures = emptyList<lecture>();

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        val PelnaNazwa: TextView
        val KrotkaNazwa: TextView
        val CzasZajec: TextView
        val card:CardView

        init{
            PelnaNazwa= itemView.findViewById<TextView>(R.id.lectureFullNameTextView)
            KrotkaNazwa=itemView.findViewById<TextView>(R.id.lectureShortNameTextView)
            CzasZajec=itemView.findViewById<TextView>(R.id.lectureDate)
            card = itemView.findViewById(R.id.lectureCardCard)

            itemView.setOnClickListener{
                it.findNavController().navigate(R.id.action_welcomeFragment2_to_groupListFragment)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.lecturecard,parent,false) as View

        return  Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.PelnaNazwa.text=lectures[position].Nazwa;
        holder.KrotkaNazwa.text=lectures[position].Skrot;

        var list = GroupVM.getListForLect(lectures[position].id)

        var str = "";
        list.forEach {
            str+=it.Data+"\n";
        }

        holder.CzasZajec.text= str;

        holder.card.setBackgroundColor(Color.parseColor(vm.backgrounds[position%vm.backgrounds.size]));
        holder.itemView.setOnClickListener{
            vm.actLecture(position);
            it.findNavController().navigate(R.id.action_welcomeFragment2_to_groupListFragment)
        }
    }

    fun setData(lects : List<lecture>) {
        this.lectures = lects
        notifyDataSetChanged()
    }

    override fun getItemCount()=lectures.size

}