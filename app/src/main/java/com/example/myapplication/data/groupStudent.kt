package com.example.myapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "groupStudent")
data class groupStudent(
    @ColumnInfo(name = "group")
    var group:Long,
    @ColumnInfo(name = "student")
    var student:Long,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Long=0L
)
