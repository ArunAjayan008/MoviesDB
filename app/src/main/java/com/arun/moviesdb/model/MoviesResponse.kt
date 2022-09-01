package com.arun.moviesdb.model

import androidx.room.Embedded
import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    val page: Int,
    @SerializedName("results")
    @Embedded
    val movies: List<Movies>,
    val total_pages: Int,
    val total_results: Int
)

