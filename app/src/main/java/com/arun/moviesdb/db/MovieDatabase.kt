package com.arun.moviesdb.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.arun.moviesdb.model.Movies
import com.arun.moviesdb.model.RemoteKey

@Database(version = 1, entities = [Movies::class, RemoteKey::class], exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun getMovieDao(): MoviesDao
    abstract fun getKeysDao(): MovieKeyDao
}