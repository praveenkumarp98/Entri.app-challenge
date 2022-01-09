package com.example.entriappchallenge.ui.home.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.entriappchallenge.data.local.room.DatabaseHelper
import com.example.entriappchallenge.data.local.room.entity.MovieEntity
import com.example.entriappchallenge.data.model.MoviesModel
import com.example.entriappchallenge.data.repository.HomeActivityRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception

class MoviePagingSource(
    private val repository: HomeActivityRepository,
    private val coroutineScope: CoroutineScope,
    private val dbHelper: DatabaseHelper,
    private var isOnline: Boolean
) : PagingSource<Int, MoviesModel.ResultModel>() {

    override fun getRefreshKey(state: PagingState<Int, MoviesModel.ResultModel>): Int? = null
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MoviesModel.ResultModel> {
        return try {
            val nextPageNumber = params.key ?: 1
            val movieListResponse = repository.getMovies(nextPageNumber)
            fetchData(nextPageNumber)
            LoadResult.Page(
                data = movieListResponse.results!!,
                prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1 ,
                nextKey = if (nextPageNumber < movieListResponse.totalPages!!)
                    movieListResponse.page?.plus(1) else null
            )
        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }

    private fun fetchData(page: Int) {
        coroutineScope.launch {
            val response = repository.getMovies(page)
            val moviesFromApi = response.results

            try {
                val moviesToInsertInDB = mutableListOf<MovieEntity>()
                if (moviesFromApi != null) {
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
                }
                dbHelper.insertAll(moviesToInsertInDB)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
}
