package com.amine.tmdb.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeysEntity(
    @PrimaryKey val movieId: Long,
    val prevKey: Int?,
    val nextKey: Int?
)