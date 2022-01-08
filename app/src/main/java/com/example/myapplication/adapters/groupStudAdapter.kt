package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MainViewModel
import com.example.myapplication.R
import com.example.myapplication.data.lecture
import com.example.myapplication.data.student

class groupStudAdapter(val vm: MainViewModel): RecyclerView.Adapter<groupStudAdapter.Holder>() {

    private var objs = emptyList<student>();

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val PelnaNazwa: CheckBox

        init {
            PelnaNazwa = itemView.findViewById<CheckBox>(R.id.lecturePositionCheckBtn)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.lecture_option, parent, false) as View

        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.PelnaNazwa.text = objs[position].Nazwisko + " " + objs[position].Imie;

        if(vm.bannedStudents.contains(objs[position].id)){
            holder.PelnaNazwa.isEnabled = false;
        }
        else if (vm.selectedStudents.contains(objs[position].id)) {
            holder.PelnaNazwa.isChecked = true;
        }


        holder.PelnaNazwa.setOnCheckedChangeListener { btnView, isChecked ->
            var list = vm.selectedStudents;
            if (list.indexOf(objs[position].id) == -1) {
                vm.selectedStudents.add(objs[position].id);
            }
            else {
                vm.selectedStudents.removeAt(list.indexOf(objs[position].id));
            }
        }
    }

    fun setData(objs: List<student>) {
        this.objs = objs
        notifyDataSetChanged()
    }

    override fun getItemCount() = objs.count()
}