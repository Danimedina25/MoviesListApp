package com.example.movielistapp.movies.data.remote.services

import com.example.movielistapp.di.utils.Constants
import com.example.movielistapp.movies.data.remote.services.response.ResponseDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MoviesListApi {
    @GET("3/movie/now_playing")
    suspend fun getMovies(@Header("accept") accept: String = "application/json",
                          @Header("Authorization") authHeader: String = Constants.header_authorization,
                          @Query("language") language: String = "en-US",
                          @Query("page") page: String = "1"): ResponseDto

}
