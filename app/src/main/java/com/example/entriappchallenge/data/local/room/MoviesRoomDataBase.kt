package com.example.entriappchallenge.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.entriappchallenge.data.local.room.dao.MovieDao
import com.example.entriappchallenge.data.local.room.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class MoviesRoomDataBase : RoomDatabase() {
    abstract val movieDao: MovieDao
    companion object {
        @Volatile
        private var INSTANCE: MoviesRoomDataBase? = null

        fun getInstance(context: Context): MoviesRoomDataBase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MoviesRoomDataBase::class.java,
                        "movies_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}