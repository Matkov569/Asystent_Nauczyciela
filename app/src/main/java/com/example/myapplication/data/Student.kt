package com.example.myapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student")
data class student (
    @ColumnInfo(name = "Imie")
    var Imie:String,
    @ColumnInfo(name = "Nazwisko")
    var Nazwisko:String,
    @ColumnInfo(name = "Album")
    var Album:Int,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Long=0L
    )