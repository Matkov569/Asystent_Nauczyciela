package com.example.myapplication.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.data.groupStudent
import com.example.myapplication.data.student
import com.example.myapplication.data.studentLecture

@Dao
interface groupStudentDao {
    @Insert
    fun insertGroupStud(groupStudent: groupStudent)

    @Delete
    fun deleteGroupStud(groupStudent: groupStudent)

    @Query("Select * from groupStudent")
    fun getAll(): MutableList<groupStudent>

    @Query("Select `group` from groupStudent Where student = :student group by `group`")
    fun getForStud(student: Long): MutableList<Long>

    @Query("Select student from groupStudent Where `group` = :group group by student")
    fun getForGroup(group: Long): MutableList<Long>

    @Query("Delete from groupStudent Where student = :student")
    fun deleteStud(student: Long)

    @Query("Delete from groupStudent Where `group` = :group")
    fun deleteGroup(group: Long)

    @Query("Select Imie, Nazwisko, Album, s.id from Student s join groupStudent gs on gs.student=s.id Where gs.`group` = :group order by s.id")
    fun getGroup(group: Long): LiveData<List<student>>

    @Query("Delete from groupStudent where 1=1")
    fun Blip()
}