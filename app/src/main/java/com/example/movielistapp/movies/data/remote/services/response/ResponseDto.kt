package com.example.movielistapp.movies.data.remote.services.response

data class ResponseDto(
    val dates: Dates,
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)