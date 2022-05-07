package com.amine.tmdb.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.amine.tmdb.data.local.dao.MovieDao
import com.amine.tmdb.data.local.entities.MovieEntity

@Database(
    entities = [MovieEntity::class],
    exportSchema = false,
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getMovieDao(): MovieDao
}