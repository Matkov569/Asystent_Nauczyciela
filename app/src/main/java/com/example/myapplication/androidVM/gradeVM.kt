package com.example.myapplication.androidVM

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.adapters.groupStudAdapter
import com.example.myapplication.dao.*
import com.example.myapplication.data.grade
import com.example.myapplication.data.group
import com.example.myapplication.data.lecture
import com.example.myapplication.database.database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class gradeVM(application: Application): AndroidViewModel(application) {
    private val dao : gradeDao
    private val gSDao : gradeStudentDao
    init{
        dao= database.getInstance(application).gradeDao
        gSDao= database.getInstance(application).gradeStudentDao
    }

    fun newGrade(obj: grade){
        viewModelScope.launch(Dispatchers.IO){
            dao.insertGrade(obj)
        }
    }

    fun update(obj: grade){
        viewModelScope.launch(Dispatchers.IO){
            dao.updateGrade(obj)
        }
    }

    fun delete(obj: grade){
        viewModelScope.launch(Dispatchers.IO){
            dao.deleteGrade(obj)
        }
    }

    fun getAll(): LiveData<List<grade>>{
        return dao.getAll();
    }

    fun getGrade(id: Long): grade{
        return dao.getGrade(id);
    }

    fun getForLecture(id: Long): List<grade>{
        return dao.getForLecture(id);
    }

    fun getLiveLecture(id: Long): LiveData<List<grade>>{
        return dao.getLiveLecture(id);
    }

    fun getList(): List<grade>{
        return dao.getList();
    }

    fun getRequired(id: Long):List<grade>{
        return dao.getRequired(id)
    }

    fun removeGrade(obj: grade){
        gSDao.deleteGrade(obj.id)
        dao.deleteGrade(obj)
    }
}