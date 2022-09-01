package com.arun.moviesdb.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.arun.moviesdb.model.RemoteKey

@Dao
interface MovieKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRemoteKeys(remoteKey: List<RemoteKey>)

    @Query("SELECT * FROM remotekey WHERE id = :id")
    suspend fun getRemoteKey(id: String): RemoteKey?

    @Query("DELETE FROM remotekey")
    suspend fun deleteAllRemoteKeys()
}