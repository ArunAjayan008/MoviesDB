package com.arun.moviesdb.api

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.arun.moviesdb.db.MovieDatabase
import com.arun.moviesdb.model.Movies
import com.arun.moviesdb.remote.MovieRemoteMediator
import com.arun.moviesdb.utils.Constants
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

}