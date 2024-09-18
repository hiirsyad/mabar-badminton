package com.lazday.mainbadminton.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [TaskModel::class],
    exportSchema = false,
    version = 2
)
abstract class DatabaseService: RoomDatabase() {
    abstract fun taskDao(): TaskDao
}