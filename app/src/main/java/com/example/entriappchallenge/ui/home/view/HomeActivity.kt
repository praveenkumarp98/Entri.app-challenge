package com.example.entriappchallenge.ui.home.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.entriappchallenge.data.local.room.DatabaseBuilder
import com.example.entriappchallenge.data.local.room.DatabaseHelperImpl
import com.example.entriappchallenge.data.local.room.entity.MovieEntity
import com.example.entriappchallenge.databinding.ActivityHomeBinding
import com.example.entriappchallenge.ui.home.adapter.RecyclerViewAdapter
import com.example.entriappchallenge.ui.home.viewmodel.HomeActivityViewModel
import com.example.entriappchallenge.utils.Constants
import com.example.entriappchallenge.utils.isOnline

class HomeActivity : AppCompatActivity(), RecyclerViewAdapter.ListItemClick {

    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerViewAdapter = RecyclerViewAdapter(this)
        binding.recyclerview.adapter = recyclerViewAdapter

        initViewModel()
        setUpObserver()
    }

    private fun initViewModel() {
        val dbHelper = DatabaseHelperImpl(DatabaseBuilder.getInstance(applicationContext))
        viewModel = ViewModelProvider(this)[HomeActivityViewModel::class.java]
        viewModel.getRecyclerListObserver().observe(this, {
            if (it != null) {
                recyclerViewAdapter.setUpdatedData(it)
            }
        })

        viewModel.makeApiCall(applicationContext, dbHelper)
    }

    private fun setUpObserver() {
        viewModel.getMovies().observe(this, {
            if (it.isEmpty()) {
                binding.recyclerview.visibility = View.GONE
            } else {
                binding.recyclerview.visibility = View.VISIBLE
                renderList(it)
            }
        })
    }

    private fun renderList(movies: List<MovieEntity>) {
        recyclerViewAdapter.setUpdatedData(movies)
        recyclerViewAdapter.notifyDataSetChanged()
    }


    override fun onListItemClick(resultModel: MovieEntity) {
        val intent = Intent(this@HomeActivity , MovieDetailsActivity::class.java)
        intent.putExtra(Constants.BundleKeys.NAME, resultModel.title)
        intent.putExtra(Constants.BundleKeys.POSTERPATH, resultModel.posterPath)
        intent.putExtra(Constants.BundleKeys.RELEASEDATE, resultModel.releaseDate)
        intent.putExtra(Constants.BundleKeys.OVERVIEW, resultModel.overview)
        startActivity(intent)
    }
}