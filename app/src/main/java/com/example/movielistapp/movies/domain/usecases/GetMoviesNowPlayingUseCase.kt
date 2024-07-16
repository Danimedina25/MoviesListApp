package com.example.movielistapp.movies.domain.usecases

import com.example.movielistapp.di.utils.NetworkResult
import com.example.movielistapp.movies.domain.model.MovieDataModel
import com.example.movielistapp.movies.domain.repository.MoviesRepository
import javax.inject.Inject

class GetMoviesNowPlayingUseCase @Inject constructor(private val repository: MoviesRepository) {

    suspend operator fun invoke(): NetworkResult<List<MovieDataModel>> {
        val result = repository.getMoviesList()
        return result
    }
}