package com.arun.moviesdb.api

import com.arun.moviesdb.model.MovieDetails
import com.arun.moviesdb.model.MoviesResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesDBApi {
    @GET("movie/popular")
    suspend fun getNowPlayingMovies(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ):MoviesResponse

    @GET("movie/{movie_id}")
    fun getMoviesDetails(
        @Path("movie_id") movieID: String,
        @Query("api_key") api_key: String,
        @Query("language") language: String,
    ): Call<MovieDetails>
}