package com.example.entriappchallenge.data.repository

import com.example.entriappchallenge.data.api.ApiService
import com.example.entriappchallenge.data.api.Utilities
import com.example.entriappchallenge.data.model.MoviesModel
import com.example.entriappchallenge.utils.Constants

class HomeActivityRepository {
    suspend fun getMovies(pageNo : Int): MoviesModel {
        val baseUrl = Constants().apiKey
        val language = Constants().language
        val retrofitInstance = Utilities.getRetrofitInstance().create(ApiService::class.java)
        return retrofitInstance.getMoviesFromApi(baseUrl, language, pageNo)
    }
}