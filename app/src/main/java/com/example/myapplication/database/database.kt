package com.example.myapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.data.*
import com.example.myapplication.dao.*

@Database(entities = [
    user::class,
    grade::class,
    lecture::class,
    group::class,
    groupStudent::class,
    toDo::class,
    student::class,
    studentLecture::class,
    gradeStudent::class
                     ],
    version = 7, exportSchema = false)
abstract class database: RoomDatabase() {

    abstract val userDao: userDao
    abstract val toDoDao: toDoDao
    abstract val lectureDao: lectureDao
    abstract val studentDao: studentDao
    abstract val gradeDao: gradeDao
    abstract val groupDao: groupDao
    abstract val studentLectureDao: studentLectureDao
    abstract val gradeStudentDao: gradeStudentDao
    abstract val groupStudentDao: groupStudentDao

    companion object {

        @Volatile
        private var INSTANCE: database? = null

        fun getInstance(context: Context): database {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        database::class.java,
                        "database"
                    )
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}