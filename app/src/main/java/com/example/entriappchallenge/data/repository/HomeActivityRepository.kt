package com.example.entriappchallenge.data.repository

import com.example.entriappchallenge.data.api.ApiService
import com.example.entriappchallenge.data.api.Utilities
import com.example.entriappchallenge.data.model.MoviesModel
import com.example.entriappchallenge.utils.Constants

class HomeActivityRepository {
    suspend fun getMovies(): MoviesModel {
        val baseUrl = Constants().apiKey
        val language = Constants().language
        val pageNo = 1
        val retrofitInstance = Utilities.getRetrofitInstance().create(ApiService::class.java)
        return retrofitInstance.getMoviesFromApi(baseUrl, language, pageNo)
    }
}