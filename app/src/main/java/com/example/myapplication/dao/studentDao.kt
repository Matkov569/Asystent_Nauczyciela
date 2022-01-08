package com.example.myapplication.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.data.student

@Dao
interface studentDao {

    @Insert
    fun insertStudent(student: student):Long

    @Delete
    fun deleteStudent(student: student)

    @Update
    fun updateStudent(student: student)

    @Query("Select * from student ORDER BY Nazwisko ASC, Imie ASC")
    fun getAllStudents(): LiveData<List<student>>

    @Query("Select * from student ORDER BY Nazwisko ASC, Imie ASC")
    fun getList(): List<student>

    @Query("Select * from student Where id = :id")
    fun getStudent(id: Long): student

    @Query("Delete from student where 1=1")
    fun Blip()

}