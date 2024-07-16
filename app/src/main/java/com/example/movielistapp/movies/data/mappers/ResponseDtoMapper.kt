package com.example.movielistapp.movies.data.mappers

import com.example.movielistapp.movies.data.remote.services.response.ResponseDto
import com.example.movielistapp.movies.domain.model.MovieDataModel


fun ResponseDto.toDomain(): List<MovieDataModel> {
    val listOfMovies = results.mapIndexed { _, result ->
        MovieDataModel(
            id = result.id,
            adult = result.adult,
            backdrop_path = result.backdrop_path,
            genre_ids = result.genre_ids,
            original_language = result.original_language,
            original_title = result.original_title,
            overview = result.overview,
            popularity = result.popularity,
            poster_path = result.poster_path,
            release_date = result.release_date,
            title = result.title,
            video = result.video,
            vote_average = result.vote_average,
            vote_count = result.vote_count
        )
    }
    return listOfMovies
}
