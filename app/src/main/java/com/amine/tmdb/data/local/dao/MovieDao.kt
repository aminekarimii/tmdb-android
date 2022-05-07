package com.amine.tmdb.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.amine.tmdb.data.local.entities.MovieEntity

@Dao
interface MovieDao : BaseDao<MovieEntity> {

}