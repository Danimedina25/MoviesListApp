package com.example.movielistapp.movies.data.remote.repository

import com.example.movielistapp.di.utils.NetworkResult
import com.example.movielistapp.movies.data.mappers.toDomain
import com.example.movielistapp.movies.data.remote.services.MoviesListApi
import com.example.movielistapp.movies.domain.model.MovieDataModel
import com.example.movielistapp.movies.domain.repository.MoviesRepository
import javax.inject.Inject

class MoviesRepositoryImpl  @Inject constructor(
    private val moviesListApi: MoviesListApi,
): MoviesRepository {

    override suspend fun getMoviesList(): NetworkResult<List<MovieDataModel>> {
        return try {
            NetworkResult.Success(
                data = moviesListApi.getMovies().toDomain()
            )
        } catch (e: Exception) {
            NetworkResult.Error(
                message = "Unknown error"
            )
        }
    }
}