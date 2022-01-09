package com.example.entriappchallenge.utils

class Constants {
    val baseUrl : String = "https://api.themoviedb.org/3/"
    val apiKey : String = "ade1d58c3b70be64984499af3b35c1a3"
    val language : String = "en-US"
    val imageBaseUrl : String = "https://image.tmdb.org/t/p/w500"

    class BundleKeys {
        companion object {
            val NAME : String = "name"
            val OVERVIEW : String = "overview"
            val RELEASEDATE : String = "releaseDate"
            val POSTERPATH : String = "posterPath"
        }
    }

}