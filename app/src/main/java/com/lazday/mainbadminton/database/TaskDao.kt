package com.lazday.mainbadminton.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {

    @Query("SELECT * FROM tableTask WHERE isPlaying=:completed")
    fun taskAll(completed: Boolean): LiveData<List<TaskModel>>

    @Query("SELECT * FROM tableTask WHERE isPlaying=:completed AND updated=:date ORDER BY updated DESC")
    fun taskAll(completed: Boolean, date: Long): LiveData<List<TaskModel>>

    @Query("SELECT * FROM tableTask WHERE isPlaying=:isPlaying ORDER BY updated DESC")
    fun allPlayer(isPlaying: Boolean): LiveData<List<TaskModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(taskModel: TaskModel)

    @Update
    fun update(taskModel: TaskModel)

    @Delete
    fun delete(taskModel: TaskModel)

    @Query("DELETE FROM tableTask WHERE isPlaying=1")
    fun deleteCompleted()

    @Query("DELETE FROM tableTask")
    fun deleteAll()
}