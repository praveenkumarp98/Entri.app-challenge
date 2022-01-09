package com.example.entriappchallenge.ui.home.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.entriappchallenge.data.local.room.DatabaseHelper
import com.example.entriappchallenge.data.local.room.entity.MovieEntity
import com.example.entriappchallenge.data.model.MoviesModel
import com.example.entriappchallenge.data.repository.HomeActivityRepository
import com.example.entriappchallenge.utils.isOnline
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeActivityViewModel(): ViewModel() {
    private var movies : MutableLiveData<List<MovieEntity>> = MutableLiveData()
    private val repository: HomeActivityRepository = HomeActivityRepository()

    fun getRecyclerListObserver(): MutableLiveData<List<MovieEntity>> {
        return movies
    }

    fun makeApiCall(context: Context, dbHelper: DatabaseHelper) {
        if (isOnline(context)) {
            Log.d("isOnline = ", "true")
            fetchMovies(dbHelper)
        } else {
            Log.d("isOnline = ", "false")
            fetchMovies(dbHelper)
        }
        /*viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getMovies()
            fetchMovies(dbHelper)
        }*/
    }

    private fun fetchMovies(dbHelper: DatabaseHelper) {
        viewModelScope.launch {
            try {
                val moviesFromDb = dbHelper.getMovies()
                if (moviesFromDb.isEmpty()) {
                    val response  = repository.getMovies()
                    var moviesFromApi = ArrayList<MoviesModel.ResultModel>()
                    if (response.results != null) {
                        moviesFromApi = response.results
                    }
                    val moviesToInsertInDB = mutableListOf<MovieEntity>()

                    for (apiMovie in moviesFromApi) {
                        val movie = MovieEntity(
                            apiMovie.id,
                            apiMovie.title,
                            apiMovie.overview,
                            apiMovie.posterPath,
                            apiMovie.releaseDate
                        )
                        moviesToInsertInDB.add(movie)
                    }
                    dbHelper.insertAll(moviesToInsertInDB)
                    Log.d("data", moviesToInsertInDB[0].toString())
                    movies.postValue(moviesToInsertInDB)
                } else {
                    movies.postValue(moviesFromDb)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getMovies(): LiveData<List<MovieEntity>> {
        return movies
    }

}
