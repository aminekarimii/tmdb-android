package com.amine.tmdb.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.amine.tmdb.data.local.model.RemoteKeysEntity

@Dao
interface RemoteKeysDao : BaseDao<RemoteKeysEntity> {

    @Query("SELECT * FROM remote_keys WHERE movieId = :repoId")
    suspend fun remoteKeysMovieId(repoId: Long): RemoteKeysEntity?

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()
}