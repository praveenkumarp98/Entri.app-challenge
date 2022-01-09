package com.example.entriappchallenge.data.local.room

import android.content.Context
import androidx.room.Room

object DatabaseBuilder {
    private var INSTANCE : MoviesRoomDataBase? = null

    fun getInstance(context: Context): MoviesRoomDataBase {
        if (INSTANCE == null) {
            synchronized(MoviesRoomDataBase::class) {
                INSTANCE = buildRoomDB(context)
            }
        }
        return INSTANCE!!
    }

    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            MoviesRoomDataBase::class.java,
            "movies_database"
        ).build()
}