package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.dao.toDoDao
import com.example.myapplication.data.group
import com.example.myapplication.data.lecture
import com.example.myapplication.data.student
import com.example.myapplication.data.toDo

class MainViewModel: ViewModel() {
    //tła
    var backgrounds = arrayOf("#67B54A", "#5E2D7A","#960144","#0B3E92", "#E88836",  "#C2977D", "#B22222" ,"#E0218C","#EAA221")

    var lectureEdit:Boolean = false;
    var studentEdit:Boolean = false;
    var groupEdit:Boolean = false;
    var gradeEdit:Boolean = false;

    private var _currentLecture: Int? = null;
    val currentLecture: Int
        get() = _currentLecture!!

    private var _currentGroup: Int? = null;
    val currentGroup: Int
        get() = _currentGroup!!

    private var _currentGrade: Long? = null;
    val currentGrade: Long
        get() = _currentGrade!!

    private var _currentStudent: Int? = null;
    val currentStudent: Int
        get() = _currentStudent!!

    var selectedLectures = mutableListOf<Long>();
    var selectedStudents = mutableListOf<Long>();
    var bannedStudents = mutableListOf<Long>();


    fun actLecture(newIndex: Int?) {
        _currentLecture = newIndex;
    }
    fun actGroup(newIndex: Int?) {
        _currentGroup = newIndex;
    }
    fun actGrade(newIndex: Long?) {
        _currentGrade = newIndex;
    }
    fun actStudent(newIndex: Int?){
        _currentStudent = newIndex;
    }

}