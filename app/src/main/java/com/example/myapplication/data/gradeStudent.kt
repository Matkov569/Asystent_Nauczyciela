package com.example.myapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gradeStudent")
data class gradeStudent(
    @ColumnInfo(name = "grade")
    var grade:Long,
    @ColumnInfo(name = "student")
    var student:Long,
    @ColumnInfo(name = "mark")
    var mark:Double,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Long=0L
)