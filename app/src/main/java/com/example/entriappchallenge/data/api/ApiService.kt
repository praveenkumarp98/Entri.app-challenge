package com.example.entriappchallenge.data.api

import com.example.entriappchallenge.data.model.MoviesModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/now_playing")
    suspend fun getMoviesFromApi(
        @Query("api_key") apiKey : String,
        @Query("language") language : String,
        @Query("page") page : Int
    ) : MoviesModel
}