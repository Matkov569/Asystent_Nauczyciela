package com.example.myapplication.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.data.student
import com.example.myapplication.data.studentLecture

@Dao
interface studentLectureDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStudLect(studentLecture: studentLecture)

    @Delete
    fun deleteStudLect(studentLecture: studentLecture)

    @Query("Select * from studentLecture")
    fun getAllStudLects(): MutableList<studentLecture>

    @Query("Select lecture from studentLecture Where student = :student")
    fun getForStud(student: Long): MutableList<Long>

    @Query("Select student from studentLecture Where lecture = :lecture")
    fun getForLect(lecture: Long): MutableList<Long>

    @Query("Delete from studentLecture Where student = :student")
    fun deleteStud(student: Long)

    @Query("Delete from studentLecture Where lecture = :lecture")
    fun deleteLect(lecture: Long)

    @Query("Select Imie, Nazwisko, Album, s.id from student s join studentLecture sl on s.id = sl.student Where lecture = :lecture group by s.id")
    fun getStudentForLect(lecture: Long): LiveData<List<student>>

    @Query("Delete from studentLecture where 1=1")
    fun Blip()
}