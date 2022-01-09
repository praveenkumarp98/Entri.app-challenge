package com.example.entriappchallenge.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.entriappchallenge.data.model.MoviesModel
import com.example.entriappchallenge.databinding.LayoutListItemMovieBinding
import com.example.entriappchallenge.utils.Constants
import com.squareup.picasso.Picasso

class ViewPagerAdapter(private var itemClick: ViewPagerAdapter.ListItemClick) : PagingDataAdapter<MoviesModel.ResultModel, ViewPagerAdapter.ViewPagerViewHolder>(MoviesComparator) {

    inner class ViewPagerViewHolder(private val binding: LayoutListItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindMovie(item : MoviesModel.ResultModel) = with(binding) {
            tvName.text = item.title
            val posterPath = item.posterPath
            val url = Constants().imageBaseUrl+posterPath
            Picasso.get()
                .load(url)
                .into(ivPoster)
        }
    }

    object MoviesComparator : DiffUtil.ItemCallback<MoviesModel.ResultModel>() {
        override fun areItemsTheSame(
            oldItem: MoviesModel.ResultModel,
            newItem: MoviesModel.ResultModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MoviesModel.ResultModel,
            newItem: MoviesModel.ResultModel
        ): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        return ViewPagerViewHolder(
            LayoutListItemMovieBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bindMovie(it) }

        holder.itemView.setOnClickListener {
            itemClick.onListItemClick(item)
        }

    }

    interface ListItemClick {
        fun onListItemClick(resultModel: MoviesModel.ResultModel?)
    }

}