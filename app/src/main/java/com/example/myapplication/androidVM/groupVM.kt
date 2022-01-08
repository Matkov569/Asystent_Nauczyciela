package com.example.myapplication.androidVM

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.dao.groupDao
import com.example.myapplication.dao.lectureDao
import com.example.myapplication.data.group
import com.example.myapplication.data.lecture
import com.example.myapplication.database.database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class groupVM(application: Application): AndroidViewModel(application) {
    private val dao : groupDao
    init{
        dao= database.getInstance(application).groupDao
    }

    fun newGroup(obj: group):Long{
        var index:Long;
        //viewModelScope.launch(Dispatchers.IO){
            index = dao.insertGroup(obj)
        //}
        return index;
    }

    fun update(obj: group){
        viewModelScope.launch(Dispatchers.IO){
            dao.updateGroup(obj)
        }
    }

    fun delete(obj: group){
        viewModelScope.launch(Dispatchers.IO){
            dao.deleteGroup(obj)
        }
    }

    fun getAll(): LiveData<List<group>>{
        return dao.getAllGroups();
    }

    fun getAllForLect(id: Long): LiveData<List<group>>{
        return dao.getAllForLect(id);
    }

    fun getList(): List<group>{
        return dao.getList();
    }

    fun getListForLect(id: Long): List<group>{
        return dao.getListForLect(id);
    }
}