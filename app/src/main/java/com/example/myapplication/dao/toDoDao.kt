package com.example.myapplication.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.data.toDo

@Dao
interface toDoDao {

    @Insert
    fun insertToDo(todo: toDo)

    @Delete
    fun deleteToDo(todo: toDo)

    @Query("Select * from toDo")
    fun getAllToDo(): LiveData<List<toDo>>

    @Query("Delete from toDo where 1=1")
    fun Blip()
}