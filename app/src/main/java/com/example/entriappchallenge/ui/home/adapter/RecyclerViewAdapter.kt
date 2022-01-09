package com.example.entriappchallenge.ui.home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.entriappchallenge.data.local.room.entity.MovieEntity
import com.example.entriappchallenge.databinding.LayoutListItemMovieBinding
import com.example.entriappchallenge.utils.Constants
import com.squareup.picasso.Picasso

class RecyclerViewAdapter(private var itemClick: ListItemClick) : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>(){

    private var items : List<MovieEntity> = ArrayList()

    fun setUpdatedData(items : List<MovieEntity>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        val binding = LayoutListItemMovieBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie = items[position]
        holder.binding.tvName.text = movie.title

        val posterPath = movie.posterPath
        val url =  Constants().imageBaseUrl+"$posterPath"
        Log.d("path = ", url)
        Picasso.get()
            .load(url)
            .into(holder.binding.ivPoster)

        holder.itemView.setOnClickListener {
            itemClick.onListItemClick(movie)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class MyViewHolder(val binding : LayoutListItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    interface ListItemClick {
        fun onListItemClick(resultModel: MovieEntity)
    }
}
