package com.arun.moviesdb.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arun.moviesdb.model.Movies
import com.arun.moviesdb.model.RemoteKey

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovies(movies: List<Movies>)

    @Query("SELECT * FROM movies")
     fun getAllMovies(): PagingSource<Int, Movies>

    @Query("DELETE FROM movies")
    suspend fun deleteAllMovies()
}