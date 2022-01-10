package com.example.entriappchallenge.ui.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.entriappchallenge.data.local.room.dbhelper.DatabaseHelper

class HomeViewModelFactory(
    private val dbHelper: DatabaseHelper,
    private var isOnline: Boolean
) : ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeActivityViewModel(dbHelper, isOnline) as T
    }
}