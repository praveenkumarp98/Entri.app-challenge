package com.example.entriappchallenge.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies_table")
data class MovieEntity(
    @PrimaryKey val id : Int?,
    @ColumnInfo(name = "title") val title : String?,
    @ColumnInfo(name = "overview") val overview : String?,
    @ColumnInfo(name = "posterPath") val posterPath : String?,
    @ColumnInfo(name = "releaseDate") val releaseDate : String?
)