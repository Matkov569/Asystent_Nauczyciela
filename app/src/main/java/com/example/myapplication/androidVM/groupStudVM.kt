package com.example.myapplication.androidVM

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.dao.groupStudentDao
import com.example.myapplication.dao.studentLectureDao
import com.example.myapplication.data.groupStudent
import com.example.myapplication.data.student
import com.example.myapplication.data.studentLecture
import com.example.myapplication.database.database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class groupStudVM(application: Application): AndroidViewModel(application) {
    private val dao : groupStudentDao
    init{
        dao= database.getInstance(application).groupStudentDao
    }

    fun insert(obj: groupStudent){
        //viewModelScope.launch(Dispatchers.IO) {
            dao.insertGroupStud(obj)
        //}
    }

    fun delete(obj: groupStudent){
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteGroupStud(obj)
        }
    }

    fun getAll(): MutableList<groupStudent>{
        return dao.getAll();
    }

    fun getForStud(obj: Long): MutableList<Long>{
        return dao.getForStud(obj);

    }

    fun getForGroup(obj: Long): MutableList<Long>{
        return dao.getForGroup(obj);
    }

    fun getGroup(obj: Long): LiveData<List<student>>{
        return dao.getGroup(obj);
    }

    fun deleteStud(obj: Long){
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteStud(obj)
        }
    }

    fun deleteGroup(obj: Long){
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteGroup(obj)
        }
    }

}