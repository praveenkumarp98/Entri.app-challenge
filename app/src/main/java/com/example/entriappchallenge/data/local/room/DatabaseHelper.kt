package com.example.entriappchallenge.data.local.room

import androidx.paging.DataSource
import com.example.entriappchallenge.data.local.room.entity.MovieEntity

interface DatabaseHelper {
    suspend fun getMovies(): List<MovieEntity>
    suspend fun insertAll(movies : List<MovieEntity>)
    fun getMovieListPaged() : DataSource.Factory<Int, MovieEntity>
}