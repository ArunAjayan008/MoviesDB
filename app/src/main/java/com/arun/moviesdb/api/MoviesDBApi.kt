package com.arun.moviesdb.api

import com.arun.moviesdb.model.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesDBApi {
    @GET("movie/popular")
    suspend fun getNowPlayingMovies(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ):MoviesResponse

    @GET("movie/popular")
    suspend fun getNowPlayingMoviesResponse(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<MoviesResponse>
}