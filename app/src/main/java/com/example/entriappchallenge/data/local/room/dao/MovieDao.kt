package com.example.entriappchallenge.data.local.room.dao

import androidx.room.*
import com.example.entriappchallenge.data.local.room.entity.MovieEntity
import androidx.paging.DataSource;


@Dao
interface MovieDao {
    @Query("SELECT * FROM movies_table")
    suspend fun getAll() : List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies : List<MovieEntity>)

    @Delete
    suspend fun delete(movie : MovieEntity)

    @Query("SELECT * FROM movies_table")
    fun getMovieListPaged(): DataSource.Factory<Int, MovieEntity>
}