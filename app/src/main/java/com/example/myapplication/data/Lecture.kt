package com.example.myapplication.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "lecture")
data class lecture (
    @ColumnInfo(name = "Nazwa")
    var Nazwa:String,
    @ColumnInfo(name = "Skrot")
    var Skrot:String,
    @Ignore
    var Grupy:List<group>?=null,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id:Long=0L
    )
{
    constructor() : this( "", "" )

    fun Czas():String{
        var str = "";

        Grupy?.forEach { str += it.Data + "\n" };

        return str;
    }
}