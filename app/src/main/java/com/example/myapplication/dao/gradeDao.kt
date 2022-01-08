package com.example.myapplication.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.data.grade
import com.example.myapplication.data.student

@Dao
interface gradeDao {

    @Insert
    fun insertGrade(grade: grade)

    @Delete
    fun deleteGrade(grade: grade)

    @Update
    fun updateGrade(grade: grade)

    @Query("Select * from grade")
    fun getAll(): LiveData<List<grade>>

    @Query("Select * from grade")
    fun getList(): List<grade>

    @Query("Select * from grade Where id = :grade")
    fun getGrade(grade: Long): grade

    @Query("Select * from grade Where lecture = :lecture")
    fun getForLecture(lecture: Long): List<grade>

    @Query("Select * from grade Where lecture = :lecture")
    fun getLiveLecture(lecture: Long): LiveData<List<grade>>

    @Query("Select * from grade Where lecture = :lecture and Wymagana = 1")
    fun getRequired(lecture: Long): List<grade>

    @Query("Delete from grade where 1=1")
    fun Blip()

}