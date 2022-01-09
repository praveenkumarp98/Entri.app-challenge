package com.example.entriappchallenge.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.entriappchallenge.data.local.room.dao.MovieDao
import com.example.entriappchallenge.data.local.room.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class MoviesRoomDataBase : RoomDatabase() {
    abstract fun movieDao() : MovieDao
}