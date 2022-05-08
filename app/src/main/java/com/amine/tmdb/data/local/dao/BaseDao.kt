package com.amine.tmdb.data.local.dao

import androidx.room.*

@Dao
interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entity: List<T>)

    @Update
    suspend fun update(entity: T)

    @Delete
    suspend fun delete(entity: T)
}