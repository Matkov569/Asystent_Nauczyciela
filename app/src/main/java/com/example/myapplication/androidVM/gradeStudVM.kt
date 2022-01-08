package com.example.myapplication.androidVM

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.dao.gradeStudentDao
import com.example.myapplication.dao.groupStudentDao
import com.example.myapplication.dao.studentLectureDao
import com.example.myapplication.data.grade
import com.example.myapplication.data.gradeStudent
import com.example.myapplication.data.groupStudent
import com.example.myapplication.data.studentLecture
import com.example.myapplication.database.database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class gradeStudVM(application: Application): AndroidViewModel(application) {
    private val dao : gradeStudentDao
    init{
        dao= database.getInstance(application).gradeStudentDao
    }

    fun insert(obj: gradeStudent){
        //viewModelScope.launch(Dispatchers.IO) {
            dao.insertGradeStud(obj)
        //}
    }

    fun delete(obj: gradeStudent){
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteGradeStud(obj)
        }
    }

    fun getAll(): LiveData<List<gradeStudent>> {
        return dao.getAll();
    }

    fun getForStud(obj: Long): List<gradeStudent>{
        return dao.getForStud(obj);
    }

    fun getRequiredForStud(obj: Long, lect: Long): List<Long>{
        return dao.getRequiredForStud(obj,lect);
    }

    fun getForGrade(obj: Long): List<gradeStudent>{
        return dao.getForGrade(obj);

    }

    fun deleteStud(obj: Long){
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteStud(obj)
        }
    }

}