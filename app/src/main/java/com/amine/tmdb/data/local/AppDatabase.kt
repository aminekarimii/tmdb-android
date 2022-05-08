package com.amine.tmdb.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.amine.tmdb.data.local.dao.MovieDao
import com.amine.tmdb.data.local.dao.RemoteKeysDao
import com.amine.tmdb.data.local.model.MovieEntity
import com.amine.tmdb.data.local.model.RemoteKeysEntity

@Database(
    entities = [MovieEntity::class, RemoteKeysEntity::class],
    exportSchema = false,
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getMovieDao(): MovieDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}