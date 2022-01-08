package com.example.myapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "grade")
data class grade(
    @ColumnInfo(name = "Nazwa")
    var Nazwa:String,
    @ColumnInfo(name = "Max")
    var Max:Int,
    @ColumnInfo(name = "Wymagana")
    var Wymagana:Boolean,
    @ColumnInfo(name = "lecture")
    var lecture:Long,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Long=0L
)