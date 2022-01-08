package com.example.myapplication.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.data.grade
import com.example.myapplication.data.student
import com.example.myapplication.data.user

@Dao
interface userDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun user(user: user)

    @Query("Select * from user limit 1")
    fun getUser():List<user>

}