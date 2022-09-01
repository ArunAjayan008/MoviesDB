package com.arun.moviesdb.api

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.arun.moviesdb.db.MovieDatabase
import com.arun.moviesdb.model.MovieDetails
import com.arun.moviesdb.model.Movies
import com.arun.moviesdb.remote.MovieRemoteMediator
import com.arun.moviesdb.utils.Constants
import com.arun.moviesdb.utils.Constants.API_KEY
import com.arun.moviesdb.utils.Constants.API_LANG
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class GeneralRepository @Inject constructor(
    private val moviesApi: MoviesDBApi,
    private val moviesDatabase: MovieDatabase
) {

    @ExperimentalPagingApi
    fun getMovies(): LiveData<PagingData<Movies>> {
        val pagingSourceFactory = { moviesDatabase.getMovieDao().getAllMovies() }
        return Pager(
            config = PagingConfig(pageSize = Constants.ITEMS_PER_PAGE),
            remoteMediator = MovieRemoteMediator(
                api = moviesApi,
                database = moviesDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).liveData
    }


    fun getMovieDetails(
        movieID: String,
        onResponse: (response_status: Boolean?, message: String?, response: MovieDetails?) -> Unit
    ) {
        try {
            moviesApi.getMoviesDetails(movieID, API_KEY, API_LANG).enqueue(object : Callback<MovieDetails> {
                override fun onResponse(
                    call: Call<MovieDetails>,
                    response: Response<MovieDetails>
                ) {
                    if (response.isSuccessful) {
                        onResponse(
                            true,
                            response.body()?.message,
                            response.body()
                        )
                    } else {
                        onResponse(false, response.body()?.message, null)
                    }
                }

                override fun onFailure(call: Call<MovieDetails>, t: Throwable) {
                    onResponse(false, "Failed Message if any", null)
                }

            })
        } catch (e: Exception) {
            e.printStackTrace()
            onResponse(false, "Failed", null)
        }
    }

}