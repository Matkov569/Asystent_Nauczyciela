package com.example.myapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "studentLecture")
data class studentLecture(
    @ColumnInfo(name = "lecture")
    var lecture:Long,
    @ColumnInfo(name = "student")
    var student:Long,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Long=0L
)
