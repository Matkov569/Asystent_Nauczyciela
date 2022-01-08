package com.example.myapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "toDo")
data class toDo(
    @ColumnInfo(name = "Tytul")
    var Tytul:String,
    @ColumnInfo(name = "Linia1")
    var LiniaP:String="",
    @ColumnInfo(name = "Linia2")
    var LiniaD:String="",
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Long=0L
)