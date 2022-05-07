package com.amine.tmdb.data.local.dao

import androidx.room.Dao
import com.amine.tmdb.data.local.model.MovieEntity

@Dao
interface MovieDao : BaseDao<MovieEntity> {

}