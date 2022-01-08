package com.example.myapplication.androidVM

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.dao.*
import com.example.myapplication.data.student
import com.example.myapplication.database.database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class studentVM(application: Application): AndroidViewModel(application) {
    private val dao : studentDao
    private val studLectDao : studentLectureDao
    //private val Dao : studentDao
    //private val groupDao : studentDao

    init{
        dao= database.getInstance(application).studentDao
        studLectDao= database.getInstance(application).studentLectureDao
    }

    var students: LiveData<List<student>> = dao.getAllStudents();

    fun newStudent(obj: student):Long{
        var index:Long;
        //viewModelScope.launch(Dispatchers.IO){
        index = dao.insertStudent(obj)
        //}
        return index;
    }

    fun updateStudent(obj: student){
        viewModelScope.launch(Dispatchers.IO){
            dao.updateStudent(obj)
        }
    }

    fun deleteStudent(obj: student){
        viewModelScope.launch(Dispatchers.IO){
            dao.deleteStudent(obj)
        }
    }

    fun getAllStudents(): LiveData<List<student>> {
        return dao.getAllStudents();
    }

    fun getList(): List<student>{
        return dao.getList();
    }

    fun getStudent(id: Long): student{
        return dao.getStudent(id);
    }

    fun removeStudent(obj: student){
        studLectDao.deleteStud(obj.id)
        dao.deleteStudent(obj);
    }

}