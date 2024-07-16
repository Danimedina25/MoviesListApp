package com.example.movielistapp.di

import com.example.movielistapp.movies.data.remote.repository.MoviesRepositoryImpl
import com.example.movielistapp.movies.domain.repository.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMovieListRepository(
        moviesRepositoryImpl: MoviesRepositoryImpl
    ): MoviesRepository
}