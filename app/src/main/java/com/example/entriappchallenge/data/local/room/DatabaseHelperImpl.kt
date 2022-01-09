package com.example.entriappchallenge.data.local.room

import com.example.entriappchallenge.data.local.room.entity.MovieEntity

class DatabaseHelperImpl(private val appDatabase: MoviesRoomDataBase) : DatabaseHelper {
    override suspend fun getMovies(): List<MovieEntity> = appDatabase.movieDao().getAll()
    override suspend fun insertAll(movies: List<MovieEntity>) = appDatabase.movieDao().insertAll(movies)

}