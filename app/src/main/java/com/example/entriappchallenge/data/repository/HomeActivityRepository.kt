package com.example.entriappchallenge.data.repository

import com.example.entriappchallenge.data.api.ApiService
import com.example.entriappchallenge.data.api.Utilities
import com.example.entriappchallenge.data.local.room.DatabaseHelper
import com.example.entriappchallenge.data.local.room.entity.MovieEntity
import com.example.entriappchallenge.data.model.MoviesModel
import com.example.entriappchallenge.utils.Constants

class HomeActivityRepository(private val dbHelper: DatabaseHelper) {
    suspend fun getMovies(pageNo : Int): MoviesModel {
        val baseUrl = Constants().apiKey
        val language = Constants().language
        val retrofitInstance = Utilities.getRetrofitInstance().create(ApiService::class.java)
        return retrofitInstance.getMoviesFromApi(baseUrl, language, pageNo)
    }

    suspend fun getMoviesFromDb() : List<MovieEntity> {
        return dbHelper.getMovies()
    }

    suspend fun addMoviesToDb(moviesToInsertInDB : MutableList<MovieEntity>) {
        dbHelper.insertAll(moviesToInsertInDB)
    }
}