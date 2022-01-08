package com.example.myapplication.androidVM

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.dao.lectureDao
import com.example.myapplication.data.lecture
import com.example.myapplication.database.database
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class lectureVM(application: Application): AndroidViewModel(application) {
    private val lectureDao : lectureDao
    init{
        lectureDao= database.getInstance(application).lectureDao
    }

    var lectures: LiveData<List<lecture>> = lectureDao.getAllLectures();

    fun newLecture(obj: lecture):Long{
        var index:Long;
        //viewModelScope.launch(Dispatchers.IO){
            index = lectureDao.insertLecture(obj)
        //}
        return index;
    }

    fun updateLecture(obj: lecture){
        viewModelScope.launch(Dispatchers.IO){
            lectureDao.updateLecture(obj)
        }
    }

    fun deleteLecture(obj: lecture){
        viewModelScope.launch(Dispatchers.IO){
            lectureDao.deleteLecture(obj)
        }
    }

    fun getAllLectures(): LiveData<List<lecture>>{
        return lectureDao.getAllLectures();
    }

    fun getList(): List<lecture>{
        return lectureDao.getList();
    }
}