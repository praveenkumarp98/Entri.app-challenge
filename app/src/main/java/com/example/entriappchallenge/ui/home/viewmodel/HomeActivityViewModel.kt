package com.example.entriappchallenge.ui.home.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.entriappchallenge.data.local.room.DatabaseHelper
import com.example.entriappchallenge.data.local.room.entity.MovieEntity
import com.example.entriappchallenge.data.model.MoviesModel
import com.example.entriappchallenge.data.repository.HomeActivityRepository
import com.example.entriappchallenge.ui.home.paging.MoviePagingSource
import com.example.entriappchallenge.utils.isOnline
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeActivityViewModel(private val dbHelper: DatabaseHelper, private var isOnline: Boolean): ViewModel() {
    private var movies : MutableLiveData<List<MovieEntity>> = MutableLiveData()
    private val repository: HomeActivityRepository = HomeActivityRepository(dbHelper)

    val moviesList = Pager(
        config = PagingConfig(pageSize = 1),
        pagingSourceFactory = {
            MoviePagingSource(repository, viewModelScope)
        }).flow.cachedIn(viewModelScope)

    fun getRecyclerListObserver(): MutableLiveData<List<MovieEntity>> {
        return movies
    }

    fun makeApiCall() {
        fetchMovies()
    }

    fun fetchMovies() {
        viewModelScope.launch {
            try {
                //val moviesFromDb = dbHelper.getMovies()
                val moviesFromDb = repository.getMoviesFromDb()
                if (moviesFromDb.isEmpty()) {
                    val response  = repository.getMovies(1)
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
                    //dbHelper.insertAll(moviesToInsertInDB)
                    repository.addMoviesToDb(moviesToInsertInDB)
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