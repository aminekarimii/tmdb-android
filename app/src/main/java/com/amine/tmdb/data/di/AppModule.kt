package com.amine.tmdb.data.di

import android.app.Application
import androidx.room.Room
import com.amine.tmdb.data.local.AppDatabase
import com.amine.tmdb.data.remote.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMovieService(): MovieService {
        return Retrofit.Builder()
            .baseUrl(MovieService.TMDB_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideDatabase(context: Application): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "tmdb_movies_db"
        ).fallbackToDestructiveMigration()
            .build()
    }
}