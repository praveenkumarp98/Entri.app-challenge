package com.example.entriappchallenge.data.api

import com.example.entriappchallenge.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Utilities {
    companion object {
        fun getRetrofitInstance() : Retrofit {
            return Retrofit.Builder()
                .baseUrl(Constants().baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}