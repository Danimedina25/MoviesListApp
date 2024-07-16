package com.example.movielistapp.movies.domain.repository

import com.example.movielistapp.di.utils.NetworkResult
import com.example.movielistapp.movies.domain.model.MovieDataModel

interface MoviesRepository {
    suspend fun getMoviesList(): NetworkResult<List<MovieDataModel>>
}

