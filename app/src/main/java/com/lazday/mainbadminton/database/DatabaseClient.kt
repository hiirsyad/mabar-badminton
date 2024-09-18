package com.lazday.mainbadminton.database

import android.content.Context
import androidx.room.Room


private const val dbName = "MabarLazday123.db"

object DatabaseClient{

    fun getService( context: Context ): DatabaseService {
        return Room.databaseBuilder(
            context,
            DatabaseService::class.java,
            dbName
        )
            .fallbackToDestructiveMigration()
            .build()
    }

}