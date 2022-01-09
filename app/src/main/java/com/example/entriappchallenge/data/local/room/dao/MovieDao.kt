package com.example.entriappchallenge.data.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.entriappchallenge.data.local.room.entity.MovieEntity

@Dao
interface MovieDao {
    @Query("SELECT * FROM MovieEntity")
    suspend fun getAll() : List<MovieEntity>

    @Insert
    suspend fun insertAll(movies : List<MovieEntity>)

    @Delete
    suspend fun delete(movie : MovieEntity)
}