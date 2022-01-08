package com.example.myapplication.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.data.group
import com.example.myapplication.data.student

@Dao
interface groupDao {

    @Insert
    fun insertGroup(group: group):Long

    @Delete
    fun deleteGroup(group: group)

    @Update
    fun updateGroup(group: group)

    @Query("Select * from `group`")
    fun getAllGroups(): LiveData<List<group>>

    @Query("Select * from `group` where lecture = :id")
    fun getAllForLect(id: Long): LiveData<List<group>>

    @Query("Select * from `group`")
    fun getList(): List<group>

    @Query("Select * from `group` where lecture = :id")
    fun getListForLect(id: Long): List<group>

    @Query("Delete from `group` where 1=1")
    fun Blip()

}