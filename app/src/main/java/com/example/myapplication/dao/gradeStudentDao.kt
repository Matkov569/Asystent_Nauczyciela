package com.example.myapplication.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.data.grade
import com.example.myapplication.data.gradeStudent
import com.example.myapplication.data.studentLecture

@Dao
interface gradeStudentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGradeStud(gradeStudent: gradeStudent)

    @Delete
    fun deleteGradeStud(gradeStudent: gradeStudent)

    @Update
    fun updateGradeStud(gradeStudent: gradeStudent)

    @Query("Select * from gradeStudent")
    fun getAll(): LiveData<List<gradeStudent>>

    @Query("Select * from gradeStudent Where student = :student")
    fun getForStud(student: Long): List<gradeStudent>

    @Query("Select gs.grade from gradeStudent gs join grade g on gs.grade = g.id Where student = :student and g.Wymagana = 1 and g.lecture = :lecture group by g.id")
    fun getRequiredForStud(student: Long, lecture: Long): List<Long>

    @Query("Select * from gradeStudent Where grade = :grade order by student")
    fun getForGrade(grade: Long): List<gradeStudent>

    @Query("Delete from gradeStudent Where student = :student")
    fun deleteStud(student: Long)

    @Query("Delete from gradeStudent Where grade = :grade")
    fun deleteGrade(grade: Long)

    @Query("Delete from gradeStudent where 1=1")
    fun Blip()
}