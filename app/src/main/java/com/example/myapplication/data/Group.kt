package com.example.myapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "group")
data class group (
    @ColumnInfo(name = "Nazwa")
    var Nazwa:String,
    @ColumnInfo(name = "Data")
    var Data:String,
    @ColumnInfo(name = "lecture")
    var lecture:Long,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Long=0L
    )
