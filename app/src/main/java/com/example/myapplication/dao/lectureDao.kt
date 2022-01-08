package com.example.myapplication.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.data.lecture

@Dao
interface lectureDao {

    @Insert
    fun insertLecture(lecture: lecture):Long

    @Update
    fun updateLecture(lecture: lecture)

    @Delete
    fun deleteLecture(lecture: lecture)

    @Query("Select * from lecture")
    fun getAllLectures(): LiveData<List<lecture>>

    @Query("Select * from lecture")
    fun getList(): List<lecture>

    @Query("Delete from lecture where 1=1")
    fun Blip()
}