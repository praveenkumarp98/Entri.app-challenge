package com.example.entriappchallenge.ui.home.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.entriappchallenge.databinding.ActivityMovieDetailsBinding
import com.example.entriappchallenge.utils.Constants
import com.squareup.picasso.Picasso

class MovieDetailsActivity : AppCompatActivity() {
    private lateinit var movieDetailsBinding: ActivityMovieDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieDetailsBinding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(movieDetailsBinding.root)

        setData(intent)

    }

    private fun setData(intent: Intent?) {
        val title : String? = intent?.getStringExtra(Constants.BundleKeys.NAME)
        val posterPath : String? = intent?.getStringExtra(Constants.BundleKeys.POSTERPATH)
        val releaseDate : String? = intent?.getStringExtra(Constants.BundleKeys.RELEASEDATE)
        val overview : String? = intent?.getStringExtra(Constants.BundleKeys.OVERVIEW)

        movieDetailsBinding.movieTitle.text = title
        movieDetailsBinding.tvReleaseDate.text = releaseDate
        movieDetailsBinding.tvOverview.text = overview
        val  url = Constants().imageBaseUrl+posterPath
        Picasso.get()
            .load(url)
            .into(movieDetailsBinding.ivMoviePoster)
    }


}