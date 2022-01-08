package com.example.myapplication.androidVM

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.dao.*
import com.example.myapplication.data.toDo
import com.example.myapplication.data.user
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.myapplication.database.database

class userVM(application: Application): AndroidViewModel(application) {
    private val dao : userDao
    private val GradeSdao : gradeStudentDao
    private val GroupDao : groupDao
    private val GradeDao : gradeDao
    private val Studentdao : studentDao
    private val SLdao : studentLectureDao
    private val GroupSdao : groupStudentDao
    private val TDdao : toDoDao
    private val lectureDao : lectureDao
    init{
        dao=database.getInstance(application).userDao
        GradeSdao=database.getInstance(application).gradeStudentDao
        GradeDao=database.getInstance(application).gradeDao
        Studentdao=database.getInstance(application).studentDao
        SLdao=database.getInstance(application).studentLectureDao
        GroupDao=database.getInstance(application).groupDao
        GroupSdao=database.getInstance(application).groupStudentDao
        TDdao=database.getInstance(application).toDoDao
        lectureDao=database.getInstance(application).lectureDao
    }

    fun user(obj: user){
        viewModelScope.launch(Dispatchers.IO){
            dao.user(obj)
        }
    }

    fun getUser():List<user>{
        return dao.getUser()
    }

    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO){
            GradeSdao.Blip()
            GradeDao.Blip()
            Studentdao.Blip()
            SLdao.Blip()
            GroupDao.Blip()
            GroupSdao.Blip()
            TDdao.Blip()
            lectureDao.Blip()
        }
    }

}