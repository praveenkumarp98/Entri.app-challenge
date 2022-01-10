package com.example.entriappchallenge.ui.home.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.entriappchallenge.data.local.room.DatabaseBuilder
import com.example.entriappchallenge.data.local.room.DatabaseHelperImpl
import com.example.entriappchallenge.data.local.room.entity.MovieEntity
import com.example.entriappchallenge.data.model.MoviesModel
import com.example.entriappchallenge.databinding.ActivityHomeBinding
import com.example.entriappchallenge.ui.home.adapter.RecyclerViewAdapter
import com.example.entriappchallenge.ui.home.adapter.ViewPagerAdapter
import com.example.entriappchallenge.ui.home.viewmodel.HomeActivityViewModel
import com.example.entriappchallenge.ui.home.viewmodel.HomeViewModelFactory
import com.example.entriappchallenge.utils.Constants
import com.example.entriappchallenge.utils.isOnline
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity(), ViewPagerAdapter.ListItemClick, RecyclerViewAdapter.ListItemClick {

    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeActivityViewModel
    lateinit var viewPagerAdapter: ViewPagerAdapter
    private var isOnline = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        isOnline = isOnline(applicationContext)
        viewPagerAdapter = ViewPagerAdapter(this)
        recyclerViewAdapter = RecyclerViewAdapter(this)

        if (isOnline) {
            binding.recyclerview.adapter = viewPagerAdapter
            setupViewModel()
            setupList()
        } else {
            binding.recyclerview.adapter = recyclerViewAdapter
            initViewModel()
            setUpObserver()
        }
    }

    private fun setupViewModel() {
        val dbHelper = DatabaseHelperImpl(DatabaseBuilder.getInstance(applicationContext))
        val factory = HomeViewModelFactory(dbHelper, isOnline)
        viewModel = ViewModelProvider(this, factory)[HomeActivityViewModel::class.java]
        viewModel.makeApiCall()
    }

    private fun setupList() {
        lifecycleScope.launch {
            viewModel.moviesList.collectLatest { pagedData ->
                viewPagerAdapter.submitData(pagedData)
            }
        }
    }

    private fun initViewModel() {
        val dbHelper = DatabaseHelperImpl(DatabaseBuilder.getInstance(applicationContext))
        val factory = HomeViewModelFactory(dbHelper, isOnline)
        viewModel = ViewModelProvider(this, factory)[HomeActivityViewModel::class.java]
        viewModel.getRecyclerListObserver().observe(this, {
            if (it != null) {
                recyclerViewAdapter.setUpdatedData(it)
            }
        })

        viewModel.makeApiCall()

    }

    private fun setUpObserver() {
        viewModel.getMovies().observe(this, {
            if (it.isEmpty()) {
                binding.recyclerview.visibility = View.GONE
            } else {
                binding.recyclerview.visibility = View.VISIBLE
                renderList(it)
                /*binding.recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)
                        if (!recyclerView.canScrollVertically(1)) {
                            if (isOnline) {
                                binding.recyclerview.adapter = viewPagerAdapter
                                setupViewModel()
                                setupList()
                            }
                        }
                    }
                })*/
            }
        })
    }

    private fun renderList(movies: List<MovieEntity>) {
        recyclerViewAdapter.setUpdatedData(movies)
        recyclerViewAdapter.notifyDataSetChanged()
    }

    override fun onListItemClick(resultModel: MoviesModel.ResultModel?) {
        val intent = Intent(this@HomeActivity , MovieDetailsActivity::class.java)
        intent.putExtra(Constants.BundleKeys.NAME, resultModel?.title)
        intent.putExtra(Constants.BundleKeys.POSTERPATH, resultModel?.posterPath)
        intent.putExtra(Constants.BundleKeys.RELEASEDATE, resultModel?.releaseDate)
        intent.putExtra(Constants.BundleKeys.OVERVIEW, resultModel?.overview)
        startActivity(intent)
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