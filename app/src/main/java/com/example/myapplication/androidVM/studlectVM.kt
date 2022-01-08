package com.example.myapplication.androidVM

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Delete
import androidx.room.Query
import com.example.myapplication.dao.studentDao
import com.example.myapplication.dao.studentLectureDao
import com.example.myapplication.data.student
import com.example.myapplication.data.studentLecture
import com.example.myapplication.database.database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class studlectVM(application: Application): AndroidViewModel(application) {
    private val dao : studentLectureDao
    init{
        dao= database.getInstance(application).studentLectureDao
    }

    fun insertStudLect(obj: studentLecture){
        viewModelScope.launch(Dispatchers.IO) {
            dao.insertStudLect(obj)
        }
    }

    fun deleteStudLect(obj: studentLecture){
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteStudLect(obj)
        }
    }

    fun getAllStudLects(): MutableList<studentLecture>{
        return dao.getAllStudLects();
    }

    fun getForStud(student: Long): MutableList<Long>{
        return dao.getForStud(student);

    }

    fun getForLect(lecture: Long): MutableList<Long>{
        return dao.getForLect(lecture);

    }

    fun deleteStud(student: Long){
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteStud(student)
        }
    }

    fun deleteLect(lecture: Long){
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteLect(lecture)
        }
    }

    fun getStudentsFromLect(obj: Long):LiveData<List<student>>{
        return dao.getStudentForLect(obj);
    }
}