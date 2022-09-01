package com.arun.moviesdb.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Query
import androidx.room.withTransaction
import com.arun.moviesdb.api.MoviesDBApi
import com.arun.moviesdb.db.MovieDatabase
import com.arun.moviesdb.model.RemoteKey
import com.arun.moviesdb.model.Movies
import com.arun.moviesdb.utils.Constants

@ExperimentalPagingApi
class MovieRemoteMediator(
    private val api: MoviesDBApi,
    private val database: MovieDatabase
) : RemoteMediator<Int, Movies>() {

    private val moviesDao = database.getMovieDao()
    private val remoteKeyDao = database.getKeysDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Movies>): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }

            val response = api.getNowPlayingMovies(
                Constants.API_KEY,
                language = Constants.API_LANG,
                page = currentPage
            )
            val endOfPaginationReached = response.equals(null)

            val prevPage = if (currentPage == 1) null else currentPage - 1
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    moviesDao.deleteAllMovies()
                    remoteKeyDao.deleteAllRemoteKeys()
                }
                val keys = response.movies.map { movies ->
                    RemoteKey(
                        id = movies.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                remoteKeyDao.insertAllRemoteKeys(remoteKey = keys)
                moviesDao.insertAllMovies(movies = response.movies)

            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Movies>
    ): RemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                remoteKeyDao.getRemoteKey(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Movies>
    ): RemoteKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { movies ->
                remoteKeyDao.getRemoteKey(id = movies.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Movies>
    ): RemoteKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { movies ->
                remoteKeyDao.getRemoteKey(id = movies.id)
            }
    }

}