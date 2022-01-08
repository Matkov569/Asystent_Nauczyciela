package com.example.myapplication.androidVM

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.myapplication.dao.toDoDao
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.toDo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.myapplication.database.database

class todoVM(application: Application): AndroidViewModel(application) {
    private val toDoDao : toDoDao
    init{
        toDoDao=database.getInstance(application).toDoDao
    }

    var toDos:LiveData<List<toDo>> = toDoDao.getAllToDo();

    fun newToDo(obj: toDo){
        viewModelScope.launch(Dispatchers.IO){
            toDoDao.insertToDo(obj)
        }
    }

    fun deleteToDo(obj: toDo){
        viewModelScope.launch(Dispatchers.IO){
            toDoDao.deleteToDo(obj)
        }
    }

    fun getAllToDo(): LiveData<List<toDo>>{
        return toDoDao.getAllToDo();
    }
}