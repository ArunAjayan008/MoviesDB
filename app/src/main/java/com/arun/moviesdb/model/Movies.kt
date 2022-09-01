package com.arun.moviesdb.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movies(
    @PrimaryKey (autoGenerate = false)
    val id: String,
    val original_language: String,
    val original_title: String,
    val poster_path: String,
    val release_date: String,
    val title: String,
)