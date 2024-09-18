package com.lazday.mainbadminton.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "tableTask")
data class TaskModel (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var name: String,
    var grade: String = "",
    var isPlaying: Boolean,
    var countPlay: Int,
    var updated: Long,
    var lastUpdate: Long
) : Serializable